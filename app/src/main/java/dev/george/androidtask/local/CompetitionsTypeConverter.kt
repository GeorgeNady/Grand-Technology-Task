package dev.george.androidtask.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.george.androidtask.model.local.CompetitionEntity
import dev.george.androidtask.model.local.ScoreEntity

class CompetitionsTypeConverter {

    @TypeConverter
    fun fromCompetitions(competitions: MutableList<CompetitionEntity>): String =
        competitions.toString()

    @TypeConverter
    fun toCompetitions(competitionsStr: String): MutableList<CompetitionEntity> {
        val result = mutableListOf<CompetitionEntity>()
        val split = competitionsStr.replace("[", "").replace("]", "").replace(" ", "").split(",")
        for (n in split) {
            try {
                val competition = Gson().fromJson(n, CompetitionEntity::class.java)
                result.add(competition)
            } catch (e: Exception) {

            }
        }
        return result
    }

    @TypeConverter
    fun fromScore(score: ScoreEntity) = score.toString()

    @TypeConverter
    fun toScore(scoreStr: String): ScoreEntity = Gson().fromJson(scoreStr, ScoreEntity::class.java)

}