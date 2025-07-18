package com.compose.base.features.account.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.compose.base.common.base.BaseViewModel
import com.compose.base.common.helper.LocalDataManager
import com.compose.base.common.helper.ResourceProvider
import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import com.compose.base.data.remote.ResultWrapper
import com.compose.base.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val localDataManager: LocalDataManager,
    resProvider: ResourceProvider,
    private val authRepository: AuthRepository,
) : BaseViewModel(localDataManager, resProvider) {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _uiState.value = LoginUiState.Error("Email and password cannot be empty.")
            return
        }
        viewModelScope.launch {
            val request = LoginRequest(_email.value, _password.value)
            when (val result = authRepository.login(request)) {
                is ResultWrapper.Loading -> {
                    setLoading(result.isLoading)
                }
                is ResultWrapper.Success<AuthResponseDto> -> {
                    _uiState.value = LoginUiState.Success
                    localDataManager.saveAccessToken(result.value.accessToken)
                }

                is ResultWrapper.Error -> {
                    displayApiError(result.error)
                }

                is ResultWrapper.Failure -> {
                    displayApiFailure(result.throwable)
                }
            }
        }
    }
}

// Sealed class for UI state
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
