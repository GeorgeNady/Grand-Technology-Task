package dev.george.androidtask.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.george.androidtask.model.domain.CompetitionGroupDomain
import dev.george.androidtask.model.remote.CompetitionsResponse
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
    // private val _competitionsMutableLiveData get() = repo.liveData
    private val _competitionsMutableLiveData = MutableLiveData<Resource<CompetitionsResponse>>()

    init {
        getRemoteCompetitions()
    }

    val competitionsLoading = Transformations.map(_competitionsMutableLiveData) {
        it?.status?.isLoading() == true
    }

    val competitionsSuccess = Transformations.map(_competitionsMutableLiveData) {
        it?.data?.asDomainModel()
    }

    val competitionsError = Transformations.map(_competitionsMutableLiveData) {
        if (it?.status?.isError() == true) it.message else null
    }

    private fun getRemoteCompetitions() = viewModelScope.launch {
        _competitionsMutableLiveData.value = Resource.loading()
        try {
            _competitionsMutableLiveData.value = repo.getRemoteCompetitions()
        } catch (e: Exception) {
            _competitionsMutableLiveData.value = Resource.failed(e.toString())
        }
    }

}