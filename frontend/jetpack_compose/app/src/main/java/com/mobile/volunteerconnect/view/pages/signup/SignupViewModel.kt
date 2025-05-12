package com.mobile.volunteerconnect.view.pages.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.model.User
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.data.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepository,
    private val userPreferences: UserPreferences  // Add UserPreferences
) : ViewModel() {

    var uiState by mutableStateOf(SignupUiState())
        private set

    fun onNameChange(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onRoleChange(role: String) {
        uiState = uiState.copy(role = role)
    }

    fun signup() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                // Add timeout for network request
                val response = withTimeout(30_000) {
                    repository.signup(
                        SignupRequest(
                            name = uiState.name,
                            email = uiState.email,
                            password = uiState.password,
                            role = uiState.role
                        )
                    )
                }

                val responseBody = response.body()
                val user = responseBody?.user
                val token = responseBody?.token

                if (response.isSuccessful && user != null && token != null) {
                    // Save data synchronously before marking success
                    saveUserData(token, user)

                    // Update UI state after successful save
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        token = token,
                        user = user
                    )
                    android.util.Log.d("AuthFlow", "Signup and data save successful")
                } else {
                    val errorMsg = responseBody?.message ?: response.message() ?: "Unknown error occurred"
                    uiState = uiState.copy(
                        isLoading = false,
                        error = errorMsg
                    )
                    android.util.Log.e("AuthFlow", "Signup failed: $errorMsg")
                }
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Unknown error occurred"
                uiState = uiState.copy(
                    isLoading = false,
                    error = errorMsg
                )
                android.util.Log.e("AuthFlow", "Signup exception: $errorMsg", e)
            }
        }
    }

    private suspend fun saveUserData(token: String, user: User) {
        try {
            withTimeout(10_000) {
                userPreferences.saveUserData(
                    token = token,
                    name = user.name,
                    email = user.email,
                    role = user.role
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthFlow", "Failed to save user data", e)
            throw e // Re-throw to handle in signup()
        }
    }

    fun resetError() {
        uiState = uiState.copy(error = null)
    }
}
