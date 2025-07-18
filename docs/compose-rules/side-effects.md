# 🔁 Jetpack Compose Side Effects

In Compose, side-effects are operations that affect something outside of the current composable scope (e.g., starting a coroutine, managing listeners, triggering a navigation event, etc.).

Use side-effect APIs (`LaunchedEffect`, `DisposableEffect`, `SideEffect`, etc.) carefully to avoid leaks and unintended behavior.

---

## ✅ Best Practices

### 🔄 1. Use `LaunchedEffect(Unit)` for One-Time Effects

This runs only once when the Composable first enters the composition.

### 📦 2. Use Stable Keys in `LaunchedEffect(key)`

When using a key (e.g., `userId`, `state`), the block will run again **only if** the key changes. Always make sure the key is stable.

### ♻️ 3. Use `DisposableEffect` for Resource Management

`DisposableEffect` allows setup and teardown when a Composable enters or leaves composition. Useful for listeners or observers.

### 🚫 4. Avoid Blocking Main Thread

Never run blocking calls (e.g., `Thread.sleep`, long loops, file IO) inside a Composable or side-effect without coroutine support.

---

## ✅ Good Examples

```kotlin
// 🔄 One-time launch
LaunchedEffect(Unit) {
    viewModel.loadData()
}

// 🔁 Re-executes when `userId` changes
LaunchedEffect(userId) {
    viewModel.loadUser(userId)
}

// 📦 Setup and cleanup with DisposableEffect
@Composable
fun LifecycleAwareComponent(context: Context) {
    val view = remember { View(context) }

    DisposableEffect(Unit) {
        val listener = View.OnAttachStateChangeListener {
            // handle attach/detach
        }

        view.addOnAttachStateChangeListener(listener)

        onDispose {
            view.removeOnAttachStateChangeListener(listener)
        }
    }
}
```
---

## ❌ Bad Examples
```
// ❌ Missing key — will re-run unnecessarily or never run as intended
LaunchedEffect(true) {
    viewModel.loadData()
}

// ❌ Triggering side-effects directly in the Composable — not lifecycle-aware
@Composable
fun BadExample() {
    viewModel.loadData() // ⚠️ This could be called multiple times!
}

// ❌ Blocking the UI thread — avoid
LaunchedEffect(Unit) {
    Thread.sleep(5000) // ⚠️ Freezes the UI!
}
```

