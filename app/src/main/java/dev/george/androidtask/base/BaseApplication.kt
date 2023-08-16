package dev.george.androidtask.base


import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import dev.george.androidtask.worker.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        lateinit var baseApplication: BaseApplication
        private const val REFRESH_DB_WORKER_NAME = "refresherRequest"
        private val applicationScope = CoroutineScope(Dispatchers.Default)
    }

    private val workManager by lazy { WorkManager.getInstance(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        baseApplication = this
        applicationScope.launch {
            workConfiguration()
        }
    }

    private fun workConfiguration() {

        // clean work constraints
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .apply {
                setRequiresDeviceIdle(true)
            }
            .build()

        // work request
        val refresherRequest = PeriodicWorkRequestBuilder<RefreshDataWork>(
            12, TimeUnit.HOURS
        ).setConstraints(constraint).build()

        workManager.enqueueUniquePeriodicWork(
            REFRESH_DB_WORKER_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            refresherRequest
        )

    }

}