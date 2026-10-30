# MyGithubUserApps 📱

An Android application built with **Jetpack Compose**, **Kotlin Coroutines & Flow**, **Hilt**, **Room**, and **Retrofit** to explore GitHub users, search accounts, view detailed profiles, and save favorite users locally.

---

## 🏗️ Architecture & Design Pattern

The application strictly follows modern Android development best practices using **Clean Architecture** combined with **MVVM (Model-View-ViewModel)**, **Unidirectional Data Flow (UDF)**, and a **Feature-Based Package Structure**.

```
id.co.evolution.mygithubuserapps/
├── core/                       # Core modules (Network, Database, Utilities)
│   ├── database/               # Room AppDatabase & DI Module
│   ├── network/                # Retrofit GithubApiService & DI Module
│   └── util/                   # Resource wrapper & helpers
├── feature/                    # Feature modules (Feature-based structure)
│   └── users/
│       ├── data/               # DTOs, Local DAO/Entities, Mappers, Repositories
│       ├── domain/             # Models, Repository Interfaces, Use Cases
│       └── presentation/       # Compose Screens, ViewModels, UI State & Events
├── navigation/                 # Jetpack Navigation Graph
└── ui/theme/                   # Material 3 Theme & Color definitions
```

### Why MVVM + UDF + Feature-Based?
- **Feature-Based Structure**: Keeps code related to a specific feature (`feature/users`) encapsulated in one place. Makes the codebase modular, easy to scale, and maintain as new features are introduced.
- **MVVM + UDF (Unidirectional Data Flow)**:
  - **User Events** (`UserListEvent`) flow from Compose UI to `ViewModel`.
  - **ViewModel** executes Use Cases and exposes an immutable `StateFlow<UiState>` (`UserListUiState`, `UserDetailUiState`).
  - **UI** observes state transitions and reactively re-renders the interface.
  - Ensures predictable UI behavior and eliminates race conditions.
- **Clean Architecture Layers (Data - Domain - Presentation)**: Decouples business logic from UI and framework APIs, enabling unit testing and flexibility.

---

## ✨ Features & Improvements

- 🔍 **GitHub User Search**: Real-time user search via GitHub REST API.
- 👥 **User List & Profiles**: View profile details including followers, following, public repositories, bio, company, and location.
- ❤️ **Favorite / Bookmark Users**: Save users locally for offline viewing.
- 🌙 **Material 3 UI**: Clean, adaptive interface designed with Jetpack Compose.
- ⚡ **Reactive State Management**: Sealed UI state representations (`Loading`, `Success`, `Error`).

---

## 🛠️ Tech Stack & Dependencies

- **Language**: [Kotlin](https://kotlinlang.org/) (2.0+)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with [Material 3](https://developer.android.com/jetpack/compose/designsystems/material3)
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Asynchronous & Streams**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [StateFlow / SharedFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
- **Local Persistence**: [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Image Loading**: [Glide Compose](https://github.com/bumptech/glide)
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/guide/navigation)

---

## 🧠 Challenges Encountered & Solutions

### Data Mapping & Local Persistence Syncing
- **Challenge**: Mapping remote network responses (`UserDto`, `UserDetailDto`) to domain models (`GithubUser`, `GithubUserDetail`) and local database entities (`UserEntity`) while ensuring local favorites/cached states stay synchronized without data desynchronization.
- **Solution**:
  - Implemented dedicated mapping functions in `UserMapper.kt` for clean model transformation across boundaries.
  - Utilized Room Database as the single source of truth for favorite status.
  - Flow-based repository observers automatically emit updated user states when a user is added to or removed from favorites.

---

## 🚀 How to Build and Run

### Prerequisites
- **Android Studio**: Ladybug (2024.2.1) or newer
- **JDK**: Version 17 or higher
- **Android SDK**: Compile SDK 37 / Min SDK 24

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/<your-username>/MyGithubUserApps.git
   cd MyGithubUserApps
   ```
2. **Open in Android Studio**:
   - Open Android Studio and select **Open**.
   - Navigate to the cloned `MyGithubUserApps` directory.
3. **Gradle Sync & Build**:
   - Allow Android Studio to complete the **Gradle Sync**.
   - Build the project by selecting `Build > Make Project` or pressing `Cmd + F9` / `Ctrl + F9`.
4. **Run the App**:
   - Select an emulator or physical Android device (Android 7.0 / API 24 or higher).
   - Click the **Run** button (`Shift + F10` / `Control + R`).

> **Note on GitHub API Rate Limits**: GitHub's unauthenticated API rate limit is 60 requests per hour. If search or profile requests fail, wait a few minutes or provide an Authorization header in `NetworkModule.kt` if necessary.

---

## 📝 License
This project is open-source and available under the [MIT License](LICENSE).
