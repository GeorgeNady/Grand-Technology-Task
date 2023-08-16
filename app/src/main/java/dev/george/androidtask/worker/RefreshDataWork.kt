package dev.george.androidtask.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.george.androidtask.repository.CompetitionsRepo
import javax.inject.Inject

class RefreshDataWork(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx,params) {

    @Inject
    lateinit var repo : CompetitionsRepo

    override suspend fun doWork(): Result {
        return try {
            repo.refreshCompetitionGroups()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}