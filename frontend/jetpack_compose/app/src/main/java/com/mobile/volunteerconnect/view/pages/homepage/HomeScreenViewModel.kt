package com.mobile.volunteerconnect.view.pages.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.Application
import com.mobile.volunteerconnect.data.model.ApplicationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getApplications()
    }

    private fun getApplications() {
        viewModelScope.launch {
            try {
                // Fetch the response (which is wrapped in ApplicationResponse)
                val response: ApplicationResponse = apiService.getApplications()

                // Update the applications state with the 'events' list from the response
                _applications.value = response.events
            } catch (e: Exception) {
                Log.e("ApplicationsViewModel", "Failed to fetch applications", e)
                _error.value = "Failed to load applications. Please try again."
            }
        }
    }
}
