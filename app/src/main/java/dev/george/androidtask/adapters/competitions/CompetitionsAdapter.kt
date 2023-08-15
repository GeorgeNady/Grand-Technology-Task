package dev.george.androidtask.adapters.competitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.george.androidtask.databinding.ItemCompetitionBinding
import dev.george.androidtask.model.domain.CompetitionDomain

class CompetitionsAdapter: ListAdapter<CompetitionDomain, CompetitionsViewHolder>(CompetitionsDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompetitionsViewHolder(
        ItemCompetitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CompetitionsViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            bCompetition = item

            /*.setOnClickListener {
                onclickListener?.let { it(item) }
            }*/
        }
    }

    private var onclickListener : ((Any) -> Unit)? = null

    fun setOnItemClickListener(listener: (Any) -> Unit) {
        onclickListener = listener
    }

}

