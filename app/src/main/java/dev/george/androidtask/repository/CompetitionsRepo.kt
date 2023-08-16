package dev.george.androidtask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.george.androidtask.local.CompetitionsDao
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import dev.george.androidtask.model.local.CompetitionsGroupEntity
import dev.george.androidtask.network.CompetitionsService
import javax.inject.Inject
import dev.george.androidtask.network.BaseDataSource
import dev.george.androidtask.network.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CompetitionGroups = Resource<List<CompetitionGroupDomain>>
typealias CompetitionGroupsMutableLiveData = MutableLiveData<CompetitionGroups>
typealias CompetitionGroupsLiveData = LiveData<CompetitionGroups>

class CompetitionsRepo @Inject constructor(
    private val competitionsService: CompetitionsService,
    private val dao: CompetitionsDao
) : BaseDataSource() {

//    private companion object {
//        private val repoIOScope = CoroutineScope(Dispatchers.IO)
//        private val repoMainScope = CoroutineScope(Dispatchers.Main)
//    }

    // private
    // private val _liveData = CompetitionGroupsMutableLiveData()

//    init {
//        getDomainCompetitions()
//    }

    // public
    // val liveData : CompetitionGroupsLiveData  get() = _liveData

    /*private fun getDomainCompetitions() = repoIOScope.launch {
        _liveData.value = Resource.loading()
        val response = getRemoteCompetitions()
        response.suspendedHandler(
            mLoading = {
                repoMainScope.launch {
                    _liveData.value = Resource.loading()
                }
            },
            mError = {
                repoMainScope.launch {
                    responseErrorHandler(
                        { _liveData.value = Resource.error(it) },
                        { result -> _liveData.value = Resource.success(result!!) }
                    )
                }
            },
            mFailed = {
                repoMainScope.launch {
                    responseErrorHandler(
                        { _liveData.value = Resource.failed(it) },
                        { result -> _liveData.value = Resource.success(result!!) }
                    )
                }
            }
        ) { res ->
            insertLocaleCompetitions(*res.asLocalModel().toTypedArray())
            repoMainScope.launch {
                _liveData.value = Resource.success(res.asDomainModel())
            }
        }
    }*/

    private fun responseErrorHandler(onError: () -> Unit, onSuccess: (List<CompetitionGroupDomain>?) -> Unit) {
        val localeData = dao.getAllCompetitions().value
        if (localeData?.isEmpty() == true) {
            onError()
        } else {
            val domainListData = localeData?.map { entity -> entity.asDomainModel() }
            onSuccess(domainListData)
        }
    }

    suspend fun getRemoteCompetitions() = safeApiCall { competitionsService.getAllCompetitions() }

    private suspend fun insertLocaleCompetitions(vararg competitionsGroups: CompetitionsGroupEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteAllCompetitions()
            val list = competitionsGroups.asList().toTypedArray()
            dao.upsertCompetitions(*list)
        }
    }

}