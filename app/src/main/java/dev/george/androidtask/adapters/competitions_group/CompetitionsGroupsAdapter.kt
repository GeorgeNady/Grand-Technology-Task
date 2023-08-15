package dev.george.androidtask.adapters.competitions_group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.george.androidtask.adapters.competitions.CompetitionsAdapter
import dev.george.androidtask.databinding.ItemCompetitionGroupBinding
import dev.george.androidtask.model.domain.CompetitionGroupDomain

class CompetitionsGroupsAdapter :
    ListAdapter<CompetitionGroupDomain, CompetitionsGroupsViewHolder>(CompetitionsGroupsDiffer) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = CompetitionsGroupsViewHolder(
        ItemCompetitionGroupBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CompetitionsGroupsViewHolder, position: Int) {
        val item = getItem(position)
        val competitionsAdapter = CompetitionsAdapter()

        holder.binding.apply {
            bCompetitionGroup = item
            rvCompetitionGroups.apply {
                adapter = competitionsAdapter
                competitionsAdapter.submitList(item.competitions)
                competitionsAdapter.setOnItemClickListener {

                }
            }
        }
    }
}