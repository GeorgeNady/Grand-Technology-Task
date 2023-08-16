package dev.george.androidtask.model.local

import dev.george.androidtask.model.domain.ScoreDomain

data class ScoreEntity(
    val homeScore: Int?,
    val awayScore: Int?
) {

    fun asDomainModel() = ScoreDomain(
        homeScore = homeScore,
        awayScore = awayScore
    )
}
