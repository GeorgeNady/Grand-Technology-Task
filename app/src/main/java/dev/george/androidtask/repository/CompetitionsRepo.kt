package dev.george.androidtask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
import timber.log.Timber

class CompetitionsRepo @Inject constructor(
    private val competitionsService: CompetitionsService,
    private val dao: CompetitionsDao
) : BaseDataSource() {

    val localCompetitionGroups = Transformations.map(dao.getAllCompetitions()) { list ->
        list?.map { it.asDomainModel() }
    }

    suspend fun refreshCompetitionGroups() = withContext(Dispatchers.IO) {
        Timber.i("refreshCompetitionGroups called")
        try {
            // api call
            Timber.i("refreshCompetitionGroups api call")
            val response = competitionsService.getAllCompetitions()

            if (response.isSuccessful) {
                // store result in the database
                val localList = response.body()?.asLocalModel() ?: emptyList()

                Timber.i("refreshCompetitionGroups delete cached local data")
                dao.deleteAllCompetitions()

                Timber.i("refreshCompetitionGroups insert new local data")
                dao.upsertCompetitions(*localList.toTypedArray())
            }

        } catch (e: Exception) {
            Timber.e("refreshCompetitionGroups $e")
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