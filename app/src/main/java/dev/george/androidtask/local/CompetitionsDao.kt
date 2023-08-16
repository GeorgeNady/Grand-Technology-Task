package dev.george.androidtask.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.george.androidtask.model.local.CompetitionsGroupEntity

@Dao
interface CompetitionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CompetitionsGroupEntity::class)
    suspend fun upsertCompetition(competition: CompetitionsGroupEntity): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CompetitionsGroupEntity::class)
    suspend fun upsertCompetitions(vararg competition: CompetitionsGroupEntity)

    @Query("SELECT * FROM competitions_group_table")
    fun getAllCompetitions(): LiveData<List<CompetitionsGroupEntity>>

    @Delete
    suspend fun deleteDailyPrayer(dailyPrayer: CompetitionsGroupEntity)

    @Query("DELETE FROM competitions_group_table")
    fun deleteAllCompetitions()
}