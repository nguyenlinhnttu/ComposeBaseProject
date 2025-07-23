package com.compose.base.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.base.R
import com.compose.base.common.helper.LocalDataManager
import com.compose.base.common.helper.ResourceProvider
import com.compose.base.data.model.response.ErrorResponse
import com.compose.base.data.remote.ResultWrapper
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.json.JSONException
import java.net.UnknownHostException

abstract class BaseViewModel(
    private val localDataManager: LocalDataManager,
    private val resProvider: ResourceProvider,
) : ViewModel() {
    // Login state observed by Compose
    // Use in compose : val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    // Do not direct access like: isLoggedIn.value
    val isLoggedIn: StateFlow<Boolean> = localDataManager.getLoginStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = false,
        )
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<Any?>(null)
    val error: StateFlow<Any?> get() = _error

    /**
     * Handles common Flow API result states such as loading, error, and failure.
     * Makes API result handling reusable across ViewModel methods.
     *
     * @param isShowLoading Whether to show or hide the loading indicator.
     * @param result The ResultWrapper from the API response.
     */
    fun handleFlowApi(isShowLoading: Boolean = true, result: ResultWrapper<*>) {
        when (result) {
            is ResultWrapper.Loading -> if (isShowLoading) setLoading(result.isLoading)
            is ResultWrapper.Error -> displayApiError(result.error)
            is ResultWrapper.Failure -> displayApiFailure(result.throwable)
            is ResultWrapper.Success<*> -> {} // Success is handled separately
        }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setError(error: Any?) {
        _error.value = error
    }

    /**
     * Handle API error with ErrorResponse
     */
    fun displayApiError(error: ErrorResponse?) {
        setError(error?.message)
    }

    /**
     * Handle API failure with Throwable
     */
    fun displayApiFailure(mThrowable: Throwable) {
        val message = when (mThrowable) {
            is JsonSyntaxException, is JSONException -> resProvider.getString(R.string.error_parser_data)
            is UnknownHostException -> resProvider.getString(R.string.error_server_connect)
            else -> resProvider.getString(R.string.error_common)
        }
        setError(message)
    }

    /**
     * One-shot access to login status (outside Compose)
     */
    suspend fun isLoggedIn(): Boolean {
        return localDataManager.getLoginStatusImmediate()
    }
}
