package com.mobile.volunteerconnect.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.EventItem
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.data.repository.OrgEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OrgEventsUiState {
    object Loading : OrgEventsUiState()
    data class Success(val events: List<EventItem>, val userName: String) : OrgEventsUiState()
    data class Error(val message: String) : OrgEventsUiState()
}

@HiltViewModel
class OrgEventsViewModel @Inject constructor(
    private val repository: OrgEventsRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrgEventsUiState>(OrgEventsUiState.Loading)
    val uiState: StateFlow<OrgEventsUiState> = _uiState

    private var allEvents: List<EventItem> = emptyList()

    init {
        loadOrgEvents()
    }

    fun loadOrgEvents() {
        viewModelScope.launch {
            _uiState.value = OrgEventsUiState.Loading
            try {
                allEvents = repository.getOrgEvents()
                val name = userPreferences.getUserName() ?: "User"
                _uiState.value = OrgEventsUiState.Success(allEvents, name)
            } catch (e: Exception) {
                _uiState.value = OrgEventsUiState.Error("Failed to load events: ${e.message}")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        val filtered = if (query.isBlank()) {
            allEvents
        } else {
            allEvents.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.subtitle.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
        }
        val name = (uiState.value as? OrgEventsUiState.Success)?.userName ?: "User"
        _uiState.value = OrgEventsUiState.Success(filtered, name)
    }
}
