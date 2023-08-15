package dev.george.androidtask.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.george.androidtask.model.domain.CompetitionDomain

class CompetitionsTypeConverter {

    @TypeConverter
    fun fromCompetition(competitions: MutableList<CompetitionDomain>?) = competitions?.forEach{
        Gson().toJson(it)!!
    }

    @TypeConverter
    fun toCompetition(competitionsStr: List<String>?) = competitionsStr?.forEach {
        Gson().fromJson(it, CompetitionDomain::class.java)!!
    }

}