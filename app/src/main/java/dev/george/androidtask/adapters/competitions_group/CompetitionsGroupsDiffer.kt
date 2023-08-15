package dev.george.androidtask.adapters.competitions_group

import androidx.recyclerview.widget.DiffUtil
import dev.george.androidtask.model.domain.CompetitionGroupDomain

object CompetitionsGroupsDiffer : DiffUtil.ItemCallback<CompetitionGroupDomain>() {
    override fun areItemsTheSame(
        oldItem: CompetitionGroupDomain,
        newItem: CompetitionGroupDomain
    ) = oldItem.uuid == newItem.uuid

    override fun areContentsTheSame(
        oldItem: CompetitionGroupDomain,
        newItem: CompetitionGroupDomain
    ) = oldItem == newItem
}