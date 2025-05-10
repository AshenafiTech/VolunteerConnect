package com.mobile.volunteerconnect.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.userApplicationsResponse
import com.mobile.volunteerconnect.data.repository.ApplicationCancelRepository
import com.mobile.volunteerconnect.data.repository.userApplicationsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.Response


//@HiltViewModel
//class MyApplicationViewModel @Inject constructor(
//    private val repo: userApplicationsRepo
//) : ViewModel() {
//
//    private val _isLoading = MutableStateFlow(true)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    private val _state = MutableStateFlow<List<userApplicationsResponse>>(emptyList())
//    val state: StateFlow<List<userApplicationsResponse>> = _state
//
//    init {
//        viewModelScope.launch {
//            try {
//                val result = repo.getUserApplications()
//                _state.value = result
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}





@HiltViewModel
class MyApplicationViewModel @Inject constructor(
    private val repo: userApplicationsRepo,
    private val applicationCancelRepo: ApplicationCancelRepository // Inject the cancel repository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _state = MutableStateFlow<List<userApplicationsResponse>>(emptyList())
    val state: StateFlow<List<userApplicationsResponse>> = _state

    private val _deleteStatus = MutableStateFlow<String?>(null) // Status of delete operation
    val deleteStatus: StateFlow<String?> = _deleteStatus

    init {
        viewModelScope.launch {
            try {
                val result = repo.getUserApplications()
                _state.value = result
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteApplication(eventId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = applicationCancelRepo.deleteApplication(eventId) // Call the delete method from the repo

            // Check if the response is successful
            if (response.isSuccessful) {
                _deleteStatus.value = "Application deleted successfully."
                // Remove the deleted application from the state
                _state.value = _state.value.filterNot { it.eventId == eventId }
            } else {
                // Handle error if delete is unsuccessful
                _deleteStatus.value = "Error deleting application: ${response.message()}"
            }

            _isLoading.value = false
        }
    }
}
