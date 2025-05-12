package com.mobile.volunteerconnect.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.EventData
import com.mobile.volunteerconnect.data.repository.EventDetailRepo
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val repository: EventDetailRepo,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var event = mutableStateOf<EventData?>(null)
        private set

    var isLoading = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var applyStatus by mutableStateOf<String?>(null)
        private set

    fun applyToEvent(eventId: Int) {
        viewModelScope.launch {
            try {
                applyStatus = null

                repository.applyToEvent(eventId)

                applyStatus = "Application successful!"
            } catch (e: Exception) {
                if (e.message?.contains("Existing application found") == true) {
                    applyStatus = "You have already applied to this event."
                } else {
                    applyStatus = "Error: ${e.message}"
                }
            }
        }
    }


    fun loadEvent(eventId: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                event.value = repository.getEventById(eventId)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}
