package com.mobile.volunteerconnect.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.UserProfileResponse
import com.mobile.volunteerconnect.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantProfileViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    // Change to MutableStateFlow and StateFlow
    private val _userProfile = MutableStateFlow<UserProfileResponse?>(null)
    val userProfile: StateFlow<UserProfileResponse?> = _userProfile

    // Fetch user profile
    fun fetchUserProfile(userId: Int) {
        viewModelScope.launch {
            val profile = userRepo.getUserProfile(userId)
            _userProfile.value = profile
        }
    }
}
