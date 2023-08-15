package dev.george.androidtask.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.george.androidtask.model.local.CompetitionsEntity

@Database(
    entities = [CompetitionsEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(value = [CompetitionsTypeConverter::class])
abstract class CompetitionsDataBase : RoomDatabase() {
    abstract fun getCompetitionsDao(): CompetitionsDao
}