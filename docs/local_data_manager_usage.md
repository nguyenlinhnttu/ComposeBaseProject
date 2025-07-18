# LocalDataManager Usage Guide

This document describes how to use `LocalDataManager` for securely storing and retrieving local app data using encryption and DataStore.

---

## 🔐 Purpose

`LocalDataManager` is responsible for:
- Encrypting and storing sensitive information like access tokens.
- Reading data as reactive `Flow`.
- Providing one-shot (immediate) access when needed (e.g., from background threads or Interceptors).

---

## 🧱 Dependencies

```kotlin
@Inject lateinit var localDataManager: LocalDataManager
```

Make sure `LocalDataManager` is provided via Dagger/Hilt.

---

## 💾 Access Token

### Save Token

```kotlin
suspend fun saveAccessToken(token: String)
```

Encrypts and saves token into `DataStore`.

```kotlin
viewModelScope.launch {
    localDataManager.saveAccessToken("abc123")
}
```

---

### Observe Token (Flow)

```kotlin
fun getAccessToken(): Flow<String?>
```

Returns decrypted access token as `Flow`.

```kotlin
val tokenFlow = localDataManager.getAccessToken()
tokenFlow.collect { token -> ... }
```

---

### Immediate Token (one-shot)

```kotlin
suspend fun getAccessTokenImmediate(): String?
```

Use when you need the token immediately, without `collect`. Typically used inside coroutine or `runBlocking`.

---

## ⏱ Using `runBlocking`

Use `runBlocking` only in safe synchronous contexts (e.g., Interceptors, unit tests):

```kotlin
val token = runBlocking {
    localDataManager.getAccessTokenImmediate()
}
```

❗ **Avoid `runBlocking` on Main Thread or inside Composables.**

---

## 📌 Notes

- Decryption failures are caught and logged.
- Never expose `MutableStateFlow` or mutable storage to the UI.
- Use Hilt injection for `LocalDataManager` wherever needed (e.g., ViewModels, Repositories).

---

## ✅ Best Practices

| Situation                | API                              | Use Inside |
|--------------------------|-----------------------------------|-------------|
| Reactive UI Binding      | `getAccessToken()`                | Compose/ViewModel |
| One-time Token Fetch     | `getAccessTokenImmediate()`       | Interceptor/Startup |
| Synchronous Fetch        | `runBlocking { getAccessTokenImmediate() }` | Interceptor, not UI |

---