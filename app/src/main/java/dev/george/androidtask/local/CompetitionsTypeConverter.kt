package dev.george.androidtask.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.george.androidtask.model.local.CompetitionEntity
import dev.george.androidtask.model.local.ScoreEntity
import timber.log.Timber

class CompetitionsTypeConverter {

    private val gson = Gson()
    private val competitionListTypeToken = object : TypeToken<List<CompetitionEntity>>() {}.type

    @TypeConverter
    fun fromCompetitions(competitions: MutableList<CompetitionEntity>?): String? =
        competitions?.let { gson.toJson(it, competitionListTypeToken) }

    @TypeConverter
    fun toCompetitions(competitionsStr: String?): MutableList<CompetitionEntity>? =
        competitionsStr?.let { gson.fromJson(it, competitionListTypeToken) }


    @TypeConverter
    fun fromScore(score: ScoreEntity) = gson.toJson(score)

    @TypeConverter
    fun toScore(scoreStr: String): ScoreEntity = Gson().fromJson(scoreStr, ScoreEntity::class.java)

}