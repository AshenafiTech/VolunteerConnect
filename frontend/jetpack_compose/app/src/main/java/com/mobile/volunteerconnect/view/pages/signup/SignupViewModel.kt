package com.mobile.volunteerconnect.view.pages.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepository
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
            uiState = uiState.copy(isLoading = true)
            try {
                val response = repository.signup(
                    SignupRequest(
                        name = uiState.name,
                        email = uiState.email,
                        password = uiState.password,
                        role = uiState.role
                    )
                )
                uiState = uiState.copy(
                    isLoading = false,
                    isSuccess = true,
                    token = response.body()?.token,
                    user = response.body()?.user
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Signup failed"
                )
            }
        }
    }

    fun resetError() {
        uiState = uiState.copy(error = null)
    }
}