package dev.george.androidtask.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import dev.george.androidtask.network.Resource
import dev.george.androidtask.repository.CompetitionsRepo
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompetitionsViewModel @Inject constructor(
    private val repo: CompetitionsRepo
) : ViewModel() {

    // private
    private lateinit var _competitionsMutableLiveData: LiveData<Resource<List<CompetitionGroupDomain>>>

    init {
        getAllCompetitions()
    }

    // public
    // info: first time load the page to show shimmer effect
    val competitionsLoading = Transformations.map(_competitionsMutableLiveData) {
        it?.status?.isLoading()
    }

    // info: list of user repos
    val competitionsSuccess = Transformations.map(_competitionsMutableLiveData) { it?.data }

    // info: when error
    val competitionsError = Transformations.map(_competitionsMutableLiveData) {
        if (it?.status?.isError() == true) it.message else null
    }

    private fun getAllCompetitions() = viewModelScope.launch { _competitionsMutableLiveData = repo.getDomainCompetitions() }

}