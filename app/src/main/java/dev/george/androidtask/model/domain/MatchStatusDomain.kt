package dev.george.androidtask.model.domain

import dev.george.androidtask.R

/**
 * SCHEDULED | TIMED | IN_PLAY | PAUSED | EXTRA_TIME | PENALTY_SHOOTOUT | FINISHED | SUSPENDED | POSTPONED | CANCELLED | AWARDED
 */
enum class MatchStatusDomain(val status: String, val background: Int) {
    SCHEDULED("SCHEDULED", R.drawable.not_started_match_bg),
    TIMED("TIMED", R.drawable.match_status_shimmer_bg),
    IN_PLAY("IN_PLAY", R.drawable.live_match_bg),
    PAUSED("PAUSED", R.drawable.match_status_shimmer_bg),
    EXTRA_TIME("EXTRA_TIME", R.drawable.match_status_shimmer_bg),
    PENALTY_SHOOTOUT("PENALTY_SHOOTOUT", R.drawable.match_status_shimmer_bg),
    FINISHED("FINISHED", R.drawable.finished_match_bg),
    SUSPENDED("SUSPENDED", R.drawable.match_status_shimmer_bg),
    POSTPONED("POSTPONED", R.drawable.match_status_shimmer_bg),
    CANCELLED("CANCELLED", R.drawable.canceled_match_bg),
    AWARDED("AWARDED", R.drawable.match_status_shimmer_bg);

    companion object {
        fun statusDomain(statusRemote: String) : MatchStatusDomain = when (statusRemote) {
            SCHEDULED.status -> SCHEDULED
            TIMED.status -> TIMED
            IN_PLAY.status -> IN_PLAY
            PAUSED.status -> PAUSED
            EXTRA_TIME.status -> EXTRA_TIME
            PENALTY_SHOOTOUT.status -> PENALTY_SHOOTOUT
            FINISHED.status -> FINISHED
            SUSPENDED.status -> SUSPENDED
            POSTPONED.status -> POSTPONED
            CANCELLED.status -> CANCELLED
            AWARDED.status -> AWARDED
            else -> SCHEDULED
        }
    }
}