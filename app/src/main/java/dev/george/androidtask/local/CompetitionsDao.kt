package dev.george.androidtask.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.george.androidtask.model.local.CompetitionsEntity

@Dao
interface CompetitionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CompetitionsEntity::class)
    suspend fun upsertCompetitions(competition: CompetitionsEntity): Long?

    @Query("SELECT * FROM competitions_table")
    fun getAllCompetitions(): LiveData<List<CompetitionsEntity>>

    @Delete
    suspend fun deleteDailyPrayer(dailyPrayer: CompetitionsEntity)

    @Query("DELETE FROM competitions_table")
    fun deleteAllCompetitions()
}