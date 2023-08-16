package dev.george.androidtask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.george.androidtask.local.CompetitionsDao
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import dev.george.androidtask.model.local.CompetitionsGroupEntity
import dev.george.androidtask.model.remote.CompetitionsResponse
import dev.george.androidtask.network.CompetitionsService
import javax.inject.Inject
import dev.george.androidtask.network.BaseDataSource
import dev.george.androidtask.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


class CompetitionsRepo @Inject constructor(
    private val competitionsService: CompetitionsService,
    private val dao: CompetitionsDao
) : BaseDataSource() {

    suspend fun getDomainCompetitions(): LiveData<Resource<List<CompetitionGroupDomain>>> {
        val domain: MutableLiveData<Resource<List<CompetitionGroupDomain>>> = MutableLiveData()
        val response = getRemoteCompetitions()
        response.suspendedHandler(
            mLoading = {
                domain.value = Resource.loading()
            },
            mError = {
                responseErrorHandler(
                    { domain.value = Resource.error(it) },
                    { result -> domain.value = Resource.success(result!!) }
                )
            },
            mFailed = {
                responseErrorHandler(
                    { domain.value = Resource.failed(it) },
                    { result -> domain.value = Resource.success(result!!) }
                )
            }
        ) { res ->
            insertLocaleCompetitions(*res.asLocalModel().toTypedArray())
            domain.value = Resource.success(res.asDomainModel())
        }
        return domain
    }

    private fun responseErrorHandler(onError: () -> Unit, onSuccess: (List<CompetitionGroupDomain>?) -> Unit) {
        val localeData = dao.getAllCompetitions().value
        if (localeData?.isEmpty() == true) {
            onError()
        } else {
            val domainListData = localeData?.map { entity -> entity.asDomainModel() }
            onSuccess(domainListData)
        }
    }

    private suspend fun getRemoteCompetitions() = safeApiCall { competitionsService.getAllCompetitions() }

    private suspend fun insertLocaleCompetitions(vararg competitionsGroups: CompetitionsGroupEntity) {
        dao.deleteAllCompetitions()
        val list = competitionsGroups.asList().toTypedArray()
        dao.upsertCompetitions(*list)
    }

}