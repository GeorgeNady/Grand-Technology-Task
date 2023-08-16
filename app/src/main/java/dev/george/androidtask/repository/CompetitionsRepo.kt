package dev.george.androidtask.repository

import androidx.lifecycle.Transformations
import dev.george.androidtask.local.CompetitionsDao
import dev.george.androidtask.model.remote.CompetitionsResponse
import dev.george.androidtask.network.BaseDataSource
import dev.george.androidtask.network.CompetitionsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class CompetitionsRepo @Inject constructor(
    private val competitionsService: CompetitionsService,
    private val dao: CompetitionsDao
) : BaseDataSource() {

    val localCompetitionGroups
        get() = Transformations.map(dao.getAllCompetitions()) { list ->
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
                insertLocaleCompetitions(response)
            }

        } catch (e: Exception) {
            Timber.e("refreshCompetitionGroups $e")
        }
    }

    suspend fun getRemoteCompetitions() = safeApiCall { competitionsService.getAllCompetitions() }

    private suspend fun insertLocaleCompetitions(response: Response<CompetitionsResponse>) {
        withContext(Dispatchers.IO) {
            dao.deleteAllCompetitions()
            val localList = response.body()?.asLocalModel() ?: emptyList()
            dao.upsertCompetitions(*localList.toTypedArray())
        }
    }

}