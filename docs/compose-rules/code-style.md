# Code Style

Consistency in naming and structure improves readability and team collaboration.

---

## ✅ Best Practices

### 1. Naming
- Use `PascalCase` for Composables and class names.
- Use `camelCase` for variables and functions.
- Prefix boolean state with `is`, `has`, or `should`.
- Callback functions should be named with `on` prefix (e.g. `onClick`, `onDismiss`).

### 2. Imports
- Avoid wildcard imports (e.g. `import androidx.compose.*`).
- Only import what's needed to improve clarity and maintainability.

### 3. Composable Functions
- Composables should be pure and small — extract UI pieces when needed.
- Parameters should be named clearly, especially for state and event handling.

### 4. Modifiers
- Chain modifiers properly, keeping style consistent.
- Order modifiers with layout first (e.g., `fillMaxSize()`) and style last (e.g., `background()`).

---

## ✅ Good Examples

```kotlin
@Composable
fun ProfileScreen(
    userName: String,
    isPremium: Boolean,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Hello, $userName")
        if (isPremium) {
            Icon(Icons.Default.Star, contentDescription = "Premium user")
        }
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}

val isLoading = remember { mutableStateOf(false) }

val onDismiss = remember { { /* handle dismiss */ } }

val paddingModifier = Modifier.padding(8.dp)
```
---
## ❌ Bad Examples
```
// ❌ Wildcard import (unclear and includes unused symbols)
import androidx.compose.*

// ❌ Inconsistent naming
@Composable
fun profile_screen() {}  // should be ProfileScreen

var Loading = true       // should be isLoading

val click = {}           // should be onClick

Button(onClick = clicker) {  // unclear naming
    Text("Press me")
}

// ❌ Modifier order inconsistent
Box(
    modifier = Modifier
        .background(Color.Red)
        .fillMaxSize() // should come before background()
)
```