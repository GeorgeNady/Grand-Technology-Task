package dev.george.androidtask.model.domain

data class CompetitionDomain(
    val id: Int,
    val status: MatchStatusDomain,
    val homeTeam: String,
    val awayTeam: String,
    val score: ScoreDomain,
    val day: String,
    val time: String,
    val league: String,
    val week: Int
)