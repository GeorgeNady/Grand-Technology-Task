package dev.george.androidtask.model.domain

import java.util.UUID

data class CompetitionGroupDomain(
    val uuid: UUID = UUID.randomUUID(),
    val weekDay: String,
    val date: String,
    val competitions: MutableList<CompetitionDomain>
)