package com.mobile.volunteerconnect.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.userApplicationsResponse
import com.mobile.volunteerconnect.data.repository.userApplicationsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyApplicationViewModel @Inject constructor(
    private val repo: userApplicationsRepo
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _state = MutableStateFlow<List<userApplicationsResponse>>(emptyList())
    val state: StateFlow<List<userApplicationsResponse>> = _state

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
}