package dev.george.androidtask.adapters.competitions

import androidx.recyclerview.widget.DiffUtil
import dev.george.androidtask.model.domain.CompetitionDomain

object CompetitionsDiffer : DiffUtil.ItemCallback<CompetitionDomain>() {
    override fun areItemsTheSame(oldItem: CompetitionDomain, newItem: CompetitionDomain) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CompetitionDomain, newItem: CompetitionDomain) =
        oldItem == newItem
}