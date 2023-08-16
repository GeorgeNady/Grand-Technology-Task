package dev.george.androidtask.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.george.androidtask.model.domain.CompetitionDomain
import dev.george.androidtask.model.domain.MatchStatusDomain
import dev.george.androidtask.model.domain.ScoreDomain

@Entity(tableName = "competitions_table")
data class CompetitionEntity(
    @PrimaryKey
    val id: Int,
    val status: MatchStatusDomain,
    val homeTeam: String,
    val awayTeam: String,
    val score: ScoreEntity,
    val day: String,
    val time: String,
    val league: String,
    val week: Int
) {

    fun asDomainModel() = CompetitionDomain(
        id = id,
        status = status,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        score = score.asDomainModel(),
        day = day,
        time = time,
        league = league,
        week = week
    )

}