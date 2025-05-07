package com.mobile.volunteerconnect.view.pages.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.applicantItem
import com.mobile.volunteerconnect.data.repository.applicantsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantsViewModel @Inject constructor(
    private val repo: applicantsRepo
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _applicants = MutableStateFlow<List<applicantItem>>(emptyList())
    val applicants: StateFlow<List<applicantItem>> = _applicants

    init {
        viewModelScope.launch {
            try {
                val result = repo.getApplicants()
                _applicants.value = result
            } finally {
                _isLoading.value = false
            }
        }
    }
}
