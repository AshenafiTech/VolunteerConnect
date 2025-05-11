package com.mobile.volunteerconnect.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.ApplicantItem
import com.mobile.volunteerconnect.data.repository.applicantsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApplicantsViewModel @Inject constructor(
    private val repository: applicantsRepo
) : ViewModel() {

    // Use MutableStateFlow for proper flow-based state management in ViewModel
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Use MutableStateFlow for lists of applicants as well
    private val _applicants = MutableStateFlow<List<ApplicantItem>>(emptyList())
    val applicants: StateFlow<List<ApplicantItem>> = _applicants

    // Fetch applicants based on the eventId
    fun fetchApplicantsByEvent(eventId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getApplicantsByEvent(eventId)
                _applicants.value = response
            } catch (e: Exception) {
                // Handle error appropriately
            } finally {
                _isLoading.value = false
            }
        }
    }
}
