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
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrgEventsUiState>(OrgEventsUiState.Loading)
    val uiState: StateFlow<OrgEventsUiState> = _uiState

    private var allEvents: List<EventItem> = emptyList()

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("All")

    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        loadOrgEvents()
    }

    fun loadOrgEvents() {
        viewModelScope.launch {
            _uiState.value = OrgEventsUiState.Loading
            try {
                allEvents = repository.getOrgEvents()
                val name = userPreferences.getUserName() ?: "User"
                applyFilters()
                _uiState.value = OrgEventsUiState.Success(allEvents, name)
            } catch (e: Exception) {
                _uiState.value = OrgEventsUiState.Error("Failed to load events: ${e.message}")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        applyFilters()
    }

    private fun applyFilters() {
        val query = _searchQuery.value.lowercase()
        val category = _selectedCategory.value

        val filtered = allEvents.filter { event ->
            val matchesCategory = category == "All" || event.category.equals(category, ignoreCase = true)
            val matchesQuery = event.title.contains(query, ignoreCase = true) ||
                    event.subtitle.contains(query, ignoreCase = true) ||
                    event.category.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }

        val name = (uiState.value as? OrgEventsUiState.Success)?.userName ?: "User"
        _uiState.value = OrgEventsUiState.Success(filtered, name)
    }
}
