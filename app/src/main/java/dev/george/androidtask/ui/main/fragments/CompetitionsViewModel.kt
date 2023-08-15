package dev.george.androidtask.ui.main.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _competitionsResponseMutableLiveData = MutableLiveData<Resource<CompetitionsResponse>>()

    init {
        getAllCompetitions()
    }

    // public
    // info: first time load the page to show shimmer effect
    val competitionsLoading = Transformations.map(_competitionsResponseMutableLiveData) {
        it?.status?.isLoading()
    }

    // info: list of user repos
    val competitionsSuccess = Transformations.map(_competitionsResponseMutableLiveData) {
        it?.data?.asDomainModel()
    }
    // info: when error
    val competitionsError = Transformations.map(_competitionsResponseMutableLiveData) {
        if (it?.status?.isError() == true) it.message else null
    }

    private fun getAllCompetitions() = viewModelScope.launch {
        _competitionsResponseMutableLiveData.value = Resource.loading()
        try {
            _competitionsResponseMutableLiveData.value = repo.getAllCompetitions()
        } catch (e: Exception) {
            Timber.e("$e")
            _competitionsResponseMutableLiveData.value = Resource.error("$e")
        }
    }

}