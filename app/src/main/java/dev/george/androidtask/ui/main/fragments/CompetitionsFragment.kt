package dev.george.androidtask.ui.main.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.george.androidtask.R
import dev.george.androidtask.adapters.competitions_group.CompetitionsGroupsAdapter
import dev.george.androidtask.base.fragment.ActivityFragmentAnnoation
import dev.george.androidtask.base.fragment.BaseFragment
import dev.george.androidtask.databinding.FragmentCompetitionsBinding

@AndroidEntryPoint
@SuppressLint("NonConstantResourceId")
@ActivityFragmentAnnoation(R.layout.fragment_competitions)
class CompetitionsFragment : BaseFragment<FragmentCompetitionsBinding>() {

    private val competitionsViewModel by viewModels<CompetitionsViewModel>()
    private val competitionsGroupsAdapter by lazy { CompetitionsGroupsAdapter() }

    override fun FragmentCompetitionsBinding.initialization() {
        setupRecyclerViews()
        lifecycleOwner = this@CompetitionsFragment
        bViewModel = competitionsViewModel
    }

    override fun FragmentCompetitionsBinding.setListener() {}

    override fun FragmentCompetitionsBinding.observers() {
        with(competitionsViewModel) {
            /*competitionsSuccess.observe(viewLifecycleOwner) {
                competitionsGroupsAdapter.submitList(it)
            }
            competitionsError.observe(viewLifecycleOwner) {
                it?.let {
                    Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }*/

            // proposal 2
            competitionsLiveData.observe(viewLifecycleOwner) {
                competitionsGroupsAdapter.submitList(it)
            }
        }
    }

    private fun FragmentCompetitionsBinding.setupRecyclerViews() {
        rvCompetitionGroups.adapter = competitionsGroupsAdapter
    }

}