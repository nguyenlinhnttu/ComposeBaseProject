# Kotlin Language Rules

This document defines Kotlin coding standards and conventions for consistency and readability across the codebase.

---

## ✩⃣ Variable Declaration

### ✅ Best Practices
- Use `val` by default. Use `var` **only** if the variable changes.
- Use explicit types for public properties and functions.

### ✅ Good
```kotlin
val userName: String = "Alice"
private var retryCount = 0
```

### ❌ Bad
```kotlin
var name = "Bob"
public var count: Int
```

---

## ✩⃣ Null Safety

### ✅ Best Practices
- Avoid `!!` operator.
- Use safe calls (`?.`) and `?:` for default values.
- Use Kotlin's null-aware features (`let`, `takeIf`, `run`) when needed.

### ✅ Good
```kotlin
val length = user?.name?.length ?: 0
user?.let {
    println(it.email)
}
```

### ❌ Bad
```kotlin
val length = user!!.name.length
```

---

## ✩⃣ Function Design

### ✅ Best Practices
- Use expression bodies for single-line functions.
- Use default and named parameters.
- Keep functions small and single-responsibility.

### ✅ Good
```kotlin
fun square(x: Int) = x * x

fun greet(name: String = "Guest") {
    println("Hello, $name!")
}

greet(name = "Linh")
```

### ❌ Bad
```kotlin
fun square(x: Int): Int {
    return x * x
}

greet("Linh")
```

---

## ✩⃣ Scoping Functions

### ✅ Best Practices
- Use `apply` to configure objects.
- Use `let` for nullable chaining.
- Avoid chaining too many scope functions.

### ✅ Good
```kotlin
val user = User().apply {
    name = "Alice"
    age = 30
}

user?.let {
    sendWelcomeEmail(it)
}
```

### ❌ Bad
```kotlin
user?.apply {
    name = "Bob"
}?.run {
    doSomething()
}?.let {
    doSomethingElse()
}
```

---

## ✩⃣ Data Classes & Immutability

### ✅ Best Practices
- Use `data class` for value holders.
- Avoid mutable fields in data classes.

### ✅ Good
```kotlin
data class User(val id: Int, val name: String)
```

### ❌ Bad
```kotlin
data class User(var name: String)
```

---

## ✩⃣ Sealed Classes and State Modeling

### ✅ Best Practices
- Use sealed classes for UI state, navigation state, etc.
- Make `when` exhaustive for safety.

### ✅ Good
```kotlin
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<User>) : UiState()
    data class Error(val message: String) : UiState()
}

when (state) {
    is UiState.Loading -> ...
    is UiState.Success -> ...
    is UiState.Error -> ...
}
```

### ❌ Bad
```kotlin
enum class State { LOADING, SUCCESS, ERROR }

when (state) {
    State.LOADING -> ...
    // Missing other cases
}
```

---

## ✩⃣ Control Flow & `when`

### ✅ Best Practices
- Prefer `when` over `if` for multiple branches.
- Always use `else` or make `when` exhaustive.

### ✅ Good
```kotlin
val result = when (input) {
    1 -> "One"
    2 -> "Two"
    else -> "Unknown"
}
```

---

## ✩⃣ Collections & Lambdas

### ✅ Best Practices
- Prefer immutability: `listOf`, `mapOf`, etc.
- Use concise lambda syntax.

### ✅ Good
```kotlin
val names = listOf("A", "B", "C").map { it.lowercase() }
```

### ❌ Bad
```kotlin
val names = ArrayList<String>()
names.add("A")
names.add("B")
```

---

## ✩⃣ String Handling

### ✅ Best Practices
- Use string templates instead of concatenation.
- Use string resources for UI strings.

### ✅ Good
```kotlin
val message = "Hello, $name!"
getString(R.string.welcome_message)
```

### ❌ Bad
```kotlin
val message = "Hello, " + name
val hardcoded = "Welcome to MyApp"
```

---

## ✩⃣ Error Handling

### ✅ Best Practices
- Use `runCatching` for safe API calls.
- Avoid catching generic `Exception`.

### ✅ Good
```kotlin
val result = runCatching { api.getData() }
    .onSuccess { data -> ... }
    .onFailure { error -> ... }
```

---

## ✩⃣ Comments & Documentation

### ✅ Best Practices
- Use KDoc for public functions and classes.
- Avoid redundant comments.

### ✅ Good
```kotlin
/**
 * Fetches user info from the remote API.
 */
fun fetchUser(): User = ...
```

### ❌ Bad
```kotlin
// fetch user
fun fetchUser(): User = ...
```

---

## ✩⃣ Naming Conventions

| Element           | Convention   |
|------------------|--------------|
| Variables         | `camelCase`  |
| Constants         | `UPPER_SNAKE_CASE` |
| Classes           | `PascalCase` |
| Composables       | `PascalCase` |
| Functions         | `camelCase()` |

---
