package com.mobile.volunteerconnect.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.volunteerconnect.data.preferences.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var isTokenValid by mutableStateOf<Boolean?>(null)
        private set

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            isTokenValid = authRepository.verifyToken()
            print("from jetpack$isTokenValid")
        }
    }


}
