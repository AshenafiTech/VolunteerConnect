package com.mobile.volunteerconnect.view.pages.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.UserProfileRequest
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.data.repository.ProfileRepository
import com.mobile.volunteerconnect.util.decodeJwtPayload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess
import kotlin.text.isNullOrEmpty

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val prefs: UserPreferences
) : ViewModel() {


    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun loadUserProfile() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                //new
                val raw = prefs.getToken()
                if (raw.isNullOrEmpty()) {
                    throw IllegalStateException("No token found")
                }
                val bearer = "Bearer $raw"
                val payload = decodeJwtPayload(raw)
                val userId = payload?.getInt("userid")
                    ?: throw IllegalStateException("JWT missing id")


                val response = repository.getUserProfile(bearer, userId)
                uiState = uiState.copy(
                    isLoading = false,
                    user = response.user
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }

    fun updateUserProfile(profileData: UserProfileRequest) {
        viewModelScope.launch {
            uiState = uiState.copy(isUpdating = true, error = null,updateSuccess = false)
            try {

                val raw = prefs.getToken()
                    ?: throw IllegalStateException("No token stored")
                val bearer = "Bearer $raw"
                val payload = decodeJwtPayload(raw)
                val userId = payload?.getInt("userid")
                    ?: throw IllegalStateException("JWT missing id")

                val response = repository.updateUserProfile(bearer, userId, profileData)
                uiState = uiState.copy(
                    isUpdating = false,
                    user = response.user,
                    updateSuccess = true,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isUpdating = false,
                    error = e.message ?: "Failed to update profile",
                    updateSuccess = false
                )
            }
        }
    }

    fun resetError() {
        uiState = uiState.copy(error = null)
    }
    fun resetUpdateSuccess() {
        uiState = uiState.copy(updateSuccess = false)
    }


    // for delete
    private val _deleteState = MutableStateFlow<DeleteState>(DeleteState.Idle)
    val deleteState = _deleteState.asStateFlow()

    fun deleteUserProfile() {
        viewModelScope.launch {
            _deleteState.value = DeleteState.Loading
            try {
                // 1. Grab raw JWT (or throw if missing)
                val raw = prefs.getToken()
                    ?: throw IllegalStateException("No token stored")

                // 2. Build the “Bearer” header
                val bearer = "Bearer $raw"

                // 3. Call your repository
                val result = repository.deleteUserProfile(bearer)

                // 4. Map into your DeleteState
                result
                    .onSuccess { response ->
                        _deleteState.value = DeleteState.Success(response.message)

                        // **BONUS**: once deletion succeeds, clear all stored user data!
                        prefs.clearUserData()
                    }
                    .onFailure { err ->
                        _deleteState.value = DeleteState.Error(
                            err.message ?: "Failed to delete profile"
                        )
                    }
            } catch (e: Exception) {
                _deleteState.value = DeleteState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    sealed class DeleteState {
        object Idle : DeleteState()
        object Loading : DeleteState()
        data class Success(val message: String) : DeleteState()
        data class Error(val message: String) : DeleteState()
    }

}

