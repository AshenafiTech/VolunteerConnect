package com.mobile.volunteerconnect.view.pages.createpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.CreateEventRequest
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) = _uiState.update { it.copy(title = title) }
    fun updateSubtitle(subtitle: String) = _uiState.update { it.copy(subtitle = subtitle) }
    fun updateCategory(category: String) = _uiState.update { it.copy(category = category) }
    fun updateDate(date: String) = _uiState.update { it.copy(date = date) }
    fun updateTime(time: String) = _uiState.update { it.copy(time = time) }
    fun updateLocation(location: String) = _uiState.update { it.copy(location = location) }
    fun updateSpotsLeft(spotsLeft: String) = _uiState.update { it.copy(spotsLeft = spotsLeft) }
    fun updateImage(image: String) = _uiState.update { it.copy(image = image) }
    fun updateDescription(description: String) = _uiState.update { it.copy(description = description) }
    fun updateRequirements(requirements: List<String>) = _uiState.update { it.copy(requirements = requirements) }
    fun updateContactPhone(contactPhone: String) = _uiState.update { it.copy(contactPhone = contactPhone) }
    fun updateContactEmail(contactEmail: String) = _uiState.update { it.copy(contactEmail = contactEmail) }
    fun updateAdditionalInfo(additionalInfo: String) = _uiState.update { it.copy(additionalInfo = additionalInfo) }
    fun updateContactTelegram(contactTelegram: String) = _uiState.update { it.copy(contactTelegram = contactTelegram) }

    fun createEvent() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val token = userPreferences.getToken()
                if (token.isNullOrEmpty()) {
                    _uiState.update { it.copy(isLoading = false, error = "Token not found") }
                    return@launch
                }

                val event = CreateEventRequest(
                    id = System.currentTimeMillis().toString(),
                    title = _uiState.value.title,
                    subtitle = _uiState.value.subtitle,
                    category = _uiState.value.category,
                    date = _uiState.value.date,
                    time = _uiState.value.time,
                    location = _uiState.value.location,
                    spotsLeft = _uiState.value.spotsLeft.toIntOrNull() ?: 0,
                    image = _uiState.value.image,
                    description = _uiState.value.description,
                    requirements = _uiState.value.requirements.joinToString(","),
                    additionalInfo = _uiState.value.additionalInfo.toString(),
                    contactPhone = _uiState.value.contactPhone,
                    contactEmail = _uiState.value.contactEmail,
                    contactTelegram = _uiState.value.contactTelegram
                )

                eventRepository.createEvent(token, event)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}