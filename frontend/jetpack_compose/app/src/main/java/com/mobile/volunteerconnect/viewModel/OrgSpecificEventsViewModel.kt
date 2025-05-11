package com.mobile.volunteerconnect.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.EventItem
import com.mobile.volunteerconnect.data.repository.OrgSpecificEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class OrgSpecificEventsViewModel @Inject constructor(
    private val orgSpecificEventsRepository: OrgSpecificEventsRepository
) : ViewModel() {

    private val _allEvents = MutableStateFlow<List<EventItem>>(emptyList())
    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val events: StateFlow<List<EventItem>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("All")

    private var searchJob: Job? = null

    fun fetchOrgEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = orgSpecificEventsRepository.getOrgSpecificEvents()
                if (response.isSuccessful) {
                    val fetchedEvents = response.body()?.events ?: emptyList()
                    _allEvents.value = fetchedEvents
                    applyFilters()  // Apply filters after events are fetched
                } else {
                    _error.value = "Failed: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            applyFilters()
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        applyFilters()
    }

    private fun applyFilters() {
        val query = _searchQuery.value.lowercase()
        val category = _selectedCategory.value

        // Show all events when search query is empty and category is "All"
        _events.value = _allEvents.value.filter { event ->
            val matchesCategory = category == "All" || event.category.equals(category, ignoreCase = true)
            val matchesQuery = event.title.contains(query, ignoreCase = true) ||
                    event.subtitle.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
}
