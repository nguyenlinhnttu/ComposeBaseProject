## ✩⃣ ViewModel Rules

### ✅ State Management
- Use `StateFlow` for persistent UI state.
- Use `SharedFlow` for one-time events (dialogs, snackbars, navigation).
- Always keep mutable state private and expose as immutable.
- Avoid using `LiveData` in new code.

### ✅ Coroutine Scope
- Always use `viewModelScope` for launching coroutines in ViewModel.
- Never use `GlobalScope` or manual coroutine jobs.

### ✅ Best Practices
- Keep ViewModel free of Android context or UI logic.
- Use sealed classes to model `UiState` and `UiEvent` clearly.
- Keep logic testable and separated from UI framework.

### ✅ Good Example
```kotlin
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localDataManager: LocalDataManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(LoginRequest(email, password))
            when (result) {
                is ResultWrapper.Loading -> _uiState.value = LoginUiState.Loading
                is ResultWrapper.Success -> {
                    localDataManager.saveAccessToken(result.value.accessToken)
                    _event.emit(LoginEvent.NavigateToHome)
                }
                is ResultWrapper.Error -> _event.emit(LoginEvent.ShowError(result.error))
                is ResultWrapper.Failure -> _event.emit(LoginEvent.ShowThrowable(result.throwable))
            }
        }
    }
}
```

### ❌ Bad Example
```kotlin
class BadViewModel : ViewModel() {
    var isLoading = false

    fun login() {
        GlobalScope.launch {
            // wrong coroutine scope
        }
    }
}
```

---

### ✅ When to Use SharedFlow
- For navigation events (`NavigateToHome`)
- Showing dialogs/snackbars (`ShowError`)
- Retry prompts, confirmations
- Any event that should not survive recomposition or screen rotation

---

### ✅ When to Use StateFlow
- For observable, continuous state
- UI loading flags, form content, fetched data
- Should represent the latest state and persist through recomposition

---

### ✅ Sample SharedFlow Dialog Event Usage
```kotlin
// Event
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    object NavigateHome : UiEvent()
}

// ViewModel
private val _event = MutableSharedFlow<UiEvent>()
val event: SharedFlow<UiEvent> = _event

fun onLoginClick() {
    viewModelScope.launch {
        _isLoading.value = true
        val result = authRepository.login(...)
        _isLoading.value = false

        if (result.isSuccess) {
            _event.emit(UiEvent.ShowToast("Login success"))
            _event.emit(UiEvent.NavigateHome)
        } else {
            _event.emit(UiEvent.ShowToast("Login failed"))
        }
    }
}

// Composable
// One-time event
LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
        when (event) {
            is UiEvent.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
            is UiEvent.NavigateHome -> {
                navController.navigate("home")
            }
        }
    }
}
```

---