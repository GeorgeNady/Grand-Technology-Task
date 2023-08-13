package dev.george.androidtask.base


import android.app.Application
//import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication: Application() {

    companion object {
        lateinit var baseApplication: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
//        Lingver.init(this, "ar")
        Timber.plant(Timber.DebugTree())
        baseApplication = this
    }

}