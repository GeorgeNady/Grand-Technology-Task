package dev.george.androidtask.model.remote

import dev.george.androidtask.R
import dev.george.androidtask.base.BaseApplication
import dev.george.androidtask.base.BaseApplication.Companion.baseApplication
import dev.george.androidtask.model.domain.CompetitionDomain
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import dev.george.androidtask.model.domain.MatchStatusDomain
import dev.george.androidtask.model.domain.ScoreDomain
import dev.george.androidtask.model.local.CompetitionEntity
import dev.george.androidtask.model.local.CompetitionsGroupEntity
import dev.george.androidtask.model.local.ScoreEntity
import dev.george.androidtask.util.DateTimeHelper
import dev.george.androidtask.util.DateTimeHelper.fromDateToLong
import dev.george.androidtask.util.DateTimeHelper.getDateFromUTCDateFormat
import dev.george.androidtask.util.DateTimeHelper.getDayOfWeekFromDate
import dev.george.androidtask.util.DateTimeHelper.getGroupDateFormatFromDate
import dev.george.androidtask.util.DateTimeHelper.getTimeFromDate
import timber.log.Timber
import java.util.regex.Pattern

/**
 * ```json
 *  {
 *      "count": 380,
 *      "filters": {},
 *      "competition": [CompetitionRemote]
 *      "matches": [
 *          [MatchRemote],
 *          [MatchRemote],
 *          [MatchRemote],
 *      ]
 *  }
 * ```
 */
data class CompetitionsResponse(
    val count: Int,
    val filters: Any,
    val competition: CompetitionRemote,
    val matches: List<MatchRemote>
) {


    /**
     * ```json
     *  {
     *      "id": 2021,
     *      "area": [AreaRemote],
     *      "name": "Premier League",
     *      "code": "PL",
     *      "plan": "TIER_ONE",
     *      "lastUpdated": "2022-03-20T08:58:54Z"
     *  }
     * ```
     */
    data class CompetitionRemote(
        val id: Int,
        val area: AreaRemote,
        val name: String,
        val code: String,
        val plan: String,
        val lastUpdated: String // 2022-03-20T08:58:54Z
    ) {

        /**
         * ```json
         *  {
         *      "id": 0,
         *      "name": "England"
         *  }
         * ```
         */
        data class AreaRemote(
            val id: Int,
            val name: String
        )
    }

    data class MatchRemote(
        val id: Int,
        val season: SeasonRemote,
        val utcDate: String, // 2023-08-11T19:00:00Z
        val status: String,
        val matchday: Int,
        val stage: String,
        val group: Any?, // nullable
        val lastUpdated: String, // 2023-08-12T01:04:51Z
        val odds: OddsRemote,
        val score: ScoreRemote,
        val homeTeam: TeamDetailsRemote,
        val awayTeam: TeamDetailsRemote,
        val referees: List<RefereeRemote>,

        ) {

        /**
         * ```json
         * {
         *    "id": 1564,
         *    "startDate": "2023-08-11",
         *    "endDate": "2024-05-19",
         *    "currentMatchday": 1
         * }
         * ```
         */
        data class SeasonRemote(
            val id: Int,
            val startDate: String, // 2023-08-11
            val endDate: String, // 2024-05-19
            val currentMatchday: Int
        )

        /**
         * ```json
         *  {
         *      "msg": "Activate Odds-Package in User-Panel to retrieve odds."
         *  }
         * ```
         */
        data class OddsRemote(
            val msg: String
        )

        /**
         *  ```json
         *  {
         *      "winner": "AWAY_TEAM",
         *      "duration": "REGULAR",
         *      "fullTime": [ScoreResultRemote]
         *      "halfTime": [ScoreResultRemote]
         *      "extraTime": [ScoreResultRemote]
         *      "penalties": [ScoreResultRemote]
         *  }
         *  ```
         */
        data class ScoreRemote(
            val winner: String?, // AWAY_TEAM || HOME_TEAM
            val duration: String, // REGULAR
            val fullTime: ScoreResultRemote,
            val halfTime: ScoreResultRemote,
            val extraTime: ScoreResultRemote,
            val penalties: ScoreResultRemote
        ) {

            /**
             * ```json
             *  {
             *      "homeTeam": 0,
             *      "awayTeam": 2
             *  }
             * ```
             */
            data class ScoreResultRemote(
                val homeTeam: Int?,
                val awayTeam: Int?
            )
        }

        /**
         * ```json
         *  {
         *      "id": 328,
         *      "name": "Burnley FC"
         *  }
         * ```
         */
        data class TeamDetailsRemote(
            val id: Int,
            val name: String
        )

        /**
         * ```json
         *  {
         *      "id": 11585,
         *      "name": "Craig Pawson",
         *      "role": "REFEREE",
         *      "nationality": "England"
         *  }
         * ```
         */
        data class RefereeRemote(
            val id: Int,
            val name: String,
            val role: String, // REFEREE
            val nationality: String
        )

        fun asDomainModel() = CompetitionDomain(
            id = id,
            status = MatchStatusDomain.statusDomain(status),
            homeTeam = homeTeam.name,
            awayTeam = awayTeam.name,
            score = ScoreDomain(
                homeScore = score.fullTime.homeTeam,
                awayScore = score.fullTime.awayTeam
            ),
            // TODO:
            // TODO:
            // TODO:
            day = "Tomorrow",
            time = getTimeFromDate(utcDate),
            league = baseApplication.getString(R.string.premier_league),
            week = matchday/7
        )

        fun asLocalModel() = CompetitionEntity(
            id = id,
            status = MatchStatusDomain.statusDomain(status),
            homeTeam = homeTeam.name,
            awayTeam = awayTeam.name,
            score = ScoreEntity(
                homeScore = score.fullTime.homeTeam,
                awayScore = score.fullTime.awayTeam
            ),
            // TODO:
            // TODO:
            // TODO:
            day = "Tomorrow",
            time = getTimeFromDate(utcDate),
            league = baseApplication.getString(R.string.premier_league),
            week = matchday/7
        )

    }

    fun asDomainModel(): List<CompetitionGroupDomain> {
        val competitionGroups = mutableListOf<CompetitionGroupDomain>()

        var cachedDate = ""

        matches.forEach { match ->

            val date = getDateFromUTCDateFormat(match.utcDate)
            val matchDomain = match.asDomainModel()

            Timber.d("""
                ------------------------------------------------------------
                CompetitionDomain =>
                $matchDomain
            """.trimIndent())

            if (cachedDate != date) {
                cachedDate = date
                competitionGroups.add(
                    CompetitionGroupDomain(
                        weekDay = getDayOfWeekFromDate(match.utcDate),
                        date = getGroupDateFormatFromDate(match.utcDate),
                        competitions = mutableListOf(),
                    )
                )
            }

            competitionGroups.last().competitions.add(matchDomain)
        }

        return competitionGroups
    }

    fun asLocalModel(): List<CompetitionsGroupEntity> {
        val competitionGroups = mutableListOf<CompetitionsGroupEntity>()

        var cachedDate = ""

        matches.forEach { match ->

            val date = getDateFromUTCDateFormat(match.utcDate)
            val matchLocal = match.asLocalModel()

            Timber.d("""
                ------------------------------------------------------------
                CompetitionEntity =>
                $matchLocal
            """.trimIndent())

            if (cachedDate != date) {
                cachedDate = date
                competitionGroups.add(
                    CompetitionsGroupEntity(
                        weekDay = getDayOfWeekFromDate(match.utcDate),
                        date = getGroupDateFormatFromDate(match.utcDate),
                        competitions = mutableListOf(),
                    )
                )
            }

            competitionGroups.last().competitions.add(matchLocal)
        }

        return competitionGroups
    }

}