package dev.george.androidtask.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import java.util.UUID

@Entity(tableName = "competitions_group_table")
data class CompetitionsGroupEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val weekDay: String,
    val date: String,
    val competitions: MutableList<CompetitionEntity>
) {

    fun asDomainModel() = CompetitionGroupDomain(
        uuid = UUID.fromString(id),
        weekDay = weekDay,
        date = date,
//        competitions = competitions.map { Gson().fromJson(it, CompetitionsEntity::class.java) }.toMutableList()
        competitions = competitions.map { it.asDomainModel() }.toMutableList()
        )
}
