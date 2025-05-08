package com.mobile.volunteerconnect.view.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.LoginRequest
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val response = repository.login(
                    LoginRequest(
                        email = uiState.email,
                        password = uiState.password
                    )
                )

                val responseBody = response.body()
                val user = responseBody?.user
                val token = responseBody?.token

                if (response.isSuccessful && user != null && token != null) {

                    userPreferences.saveUserData(
                        token = token,
                        name = user.name,
                        email = user.email,
                        role = user.role
                    )

                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        token = token,
                        user = user
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = response.message()
                    )
                }

            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun resetError() {
        uiState = uiState.copy(error = null)
    }
}
