package dev.george.androidtask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.george.androidtask.BuildConfig
import dev.george.androidtask.local.CompetitionsDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideGoodsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CompetitionsDataBase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideCompetitionsDao(db: CompetitionsDataBase) = db.getCompetitionsDao()

}