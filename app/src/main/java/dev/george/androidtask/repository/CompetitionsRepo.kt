package dev.george.androidtask.repository

import dev.george.androidtask.local.CompetitionsDao
import dev.george.androidtask.model.remote.CompetitionsResponse
import dev.george.androidtask.network.CompetitionsService
import javax.inject.Inject
import dev.george.androidtask.network.BaseDataSource
import dev.george.androidtask.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CompetitionsRepo @Inject constructor(
    private val competitionsService: CompetitionsService,
    private val dao: CompetitionsDao
) : BaseDataSource() {

    suspend fun getAllCompetitions(): Resource<CompetitionsResponse> =
        safeApiCall { competitionsService.getAllCompetitions() }

    suspend fun deleteAllCompetitions() = withContext(Dispatchers.IO) {
        dao.deleteAllCompetitions()
    }

}