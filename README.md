
# 🚀 MyComposeApp

A modern Android application built with **Jetpack Compose**


---

## 🛠 Recommended Setup

- Android Studio Narwhal | 2025.1.1 or newer

---

## 📱 Architecture & Tech Stack

- **Material 3 UI** — Modern UI components with `androidx.compose.material3`
- **Jetpack Compose** — Declarative and reactive UI development
- **Clean MVVM Architecture** — Separation of concerns with ViewModel, Repository, and UI
- **Modular features/** — Each feature (e.g., Home, Account) is isolated for scalability
- **Hilt (DI)** — Dependency Injection for easier testing and decoupling
- **Retrofit** — Type-safe HTTP client for API communication
- **DataStore** — Modern key-value storage replacing SharedPreferences
- **Kotlin Coroutines** - Simplified async code, Flow for reactive streams, structured concurrency
---

## 📂 Project Structure

```
com.compose.base/
├── common/ # Base classes, constants, extensions, utils, DI setup
├── data/ # Data layer (models, remote sources, repositories)
├── features/ # Feature modules (account, home, splash, etc.)
│ ├── account/
│ ├── home/
│ ├── main_tab/
│ ├── my_data/
│ ├── shared/
│ └── splash/
├── navigation/ # App navigation setup
├── ui/ # Theme, components, etc.)
└── MainActivity.kt # App entry point
```

---

## 🧭 App Navigation

```
Navigation
├── AppNavGraph # root navigation
├── TabNavGraph # bottom tabs navigation
````

---


## Format Code before commit

```
format:
	./gradlew ktlintFormat
````
---


## Build App via Fastlane

```
buildDev:
	fastlane ota env:dev mode:release or debug
	
buildStaging:
	fastlane ota env:staging mode:release or debug

buildProd:
	fastlane ota env:prod mode:release 

````