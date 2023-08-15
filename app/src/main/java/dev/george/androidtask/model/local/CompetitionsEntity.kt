package dev.george.androidtask.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import dev.george.androidtask.model.domain.CompetitionDomain
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import java.util.UUID

@Entity(tableName = "competitions_table")
data class CompetitionsEntity(
    @PrimaryKey
    val id: String,
    val weekDay: String,
    val date: String,
    val competitions: MutableList<String>
) {

    fun asDomainModel() = CompetitionGroupDomain(
        uuid = UUID.fromString(id),
        weekDay = weekDay,
        date = date,
        competitions = competitions.map { Gson().fromJson(it, CompetitionDomain::class.java) }.toMutableList()
    )
}
