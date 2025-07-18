# 🧱 Architecture & ViewModel Guidelines

A well-defined architecture with clear separation of concerns improves maintainability, scalability, and testability in Compose apps.

---

## ✅ Best Practices

### 🔄 Unidirectional Data Flow (UDF)

- State flows in one direction: **ViewModel → UI → User Action → ViewModel**
- Avoid letting Composables directly manipulate state or business logic.

### 🧠 ViewModel Responsibilities

- Owns and exposes all UI-related state.
- Handles business logic and interacts with repositories or use cases.
- Emits state through `StateFlow`, `LiveData`, or `mutableStateOf`.

### 🎨 UI Responsibilities

- Displays state from the ViewModel.
- Sends events or user actions to the ViewModel via callbacks or `LaunchedEffect`.

### 🚫 Anti-patterns to Avoid

- API/database calls directly inside Composables.
- Creating state with `remember { mutableStateOf(...) }` inside Composables if it should persist across configuration changes.
- Mixing logic and UI in a single Composable function.

---

## ✅ Good Example

```kotlin
// LoginViewModel.kt
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onEmailChanged(newValue: String) {
        email = newValue
    }

    fun onPasswordChanged(newValue: String) {
        password = newValue
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val result = authRepository.login(email, password)
            isLoading = false

            result.onSuccess { onSuccess() }
                .onFailure { onError(it.message ?: "Unknown error") }
        }
    }
}


// LoginScreen.kt
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val email = viewModel.email
    val password = viewModel.password
    val isLoading = viewModel.isLoading

    Column(Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("Password") }
        )
        Button(
            onClick = {
                viewModel.login(
                    onSuccess = onNavigateHome,
                    onError = { /* Show snackbar */ }
                )
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(16.dp))
            } else {
                Text("Login")
            }
        }
    }
}
```
---
## ❌ Bad Example
```
@Composable
fun BadLoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Button(onClick = {
        // ❌ API call inside Composable
        runBlocking {
            val result = AuthRepository().login(email, password)
        }
    }) {
        Text("Login")
    }
}
```
