# 🧠 State Management in Jetpack Compose

Effective state management is key to building predictable and maintainable Compose apps. Always distinguish between **UI state**, **business logic**, and **temporary local state**.

---

## ✅ Best Practices

### 🗂 1. Keep State in ViewModel
- Store shared UI state, loading indicators, error flags, and business logic in a `ViewModel`.
- This ensures state survives recomposition and process death (with `SavedStateHandle` or `rememberSaveable`).

### 🧠 2. Use `remember` for Temporary UI State
- For short-lived state local to a Composable (e.g., dialog visibility), use `remember`.

### 💾 3. Use `rememberSaveable` for State Across Configuration Changes
- To persist UI state across device rotation or backgrounding (e.g., form inputs), use `rememberSaveable`.

### 🔄 4. Use `derivedStateOf` for Optimized Computations
- When state depends on other states, wrap it in `derivedStateOf` to avoid unnecessary recompositions.

### ⚠️ 5. Avoid Direct `mutableStateOf` Without `remember`
- Using `mutableStateOf` directly in a Composable without `remember` causes the value to reset on every recomposition.

### 🧼 6. Do Not Manage API/Loading/Error State in Composables
- Delegate those concerns to the ViewModel to maintain clean UI logic.

---

## ✅ Good Examples

```kotlin
// ✅ ViewModel holds state and business logic
class MyViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set

    fun increment() {
        count++
    }
}

@Composable
fun CounterScreen(viewModel: MyViewModel = viewModel()) {
    val count = viewModel.count
    Button(onClick = viewModel::increment) {
    
// ✅ Local UI-only state (e.g., dialog visibility)
val isDialogOpen = remember { mutableStateOf(false) }

// ✅ State survives configuration changes
val email = rememberSaveable { mutableStateOf("") }

// ✅ Efficient derived state
val isLoginEnabled by remember {
    derivedStateOf { username.isNotBlank() && password.isNotBlank() }
}
```
---
## ❌ Bad Examples
```
// ❌ Not remembered — gets reset on recomposition
val name = mutableStateOf("John") // Should be wrapped in remember

// ❌ Composable holding loading logic — violates separation of concerns
val isLoading = remember { mutableStateOf(true) } // Should be managed in ViewModel


// ❌ State used without backing ViewModel — state disappears on recomposition
@Composable
fun Counter() {
    var count = mutableStateOf(0) // Wrong
    Button(onClick = { count.value++ }) {
        Text("Count: ${count.value}")
    }
}
```
