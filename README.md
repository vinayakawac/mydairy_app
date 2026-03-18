<div align="center">

# MyDiary

### A private, offline-first personal diary for Android

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![API](https://img.shields.io/badge/Min%20SDK-26-orange.svg)](https://developer.android.com/about/versions/oreo)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)](https://developer.android.com/jetpack/compose)

---

*Your thoughts stay on your device. No cloud. No accounts. No tracking.*

</div>

---

## Table of Contents

- [About](#about)
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)
- [Performance Targets](#performance-targets)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

---

## About

**MyDiary** is a clean, modern Android diary application built entirely with Kotlin and Jetpack Compose. It is designed for users who value privacy above all else -- every piece of data stays on the device. There are no servers, no syncing, no analytics, and no internet permission required.

Write your thoughts, attach photos, organize with tags, and browse your history through a calendar view -- all in a fast, Material 3 interface that respects your system theme or lets you choose your own.

---

## Features

| Feature | Description |
|:---|:---|
| **Diary Entries** | Create, edit, and delete entries with a title, body text, and timestamps |
| **Photo Attachments** | Attach multiple photos per entry from the gallery or camera |
| **Tags and Categories** | Create custom tags, assign them to entries, and filter your diary by tag |
| **Full-Text Search** | Instantly search across all entry titles and body text using FTS5 |
| **Calendar View** | Browse entries by date with a month grid that highlights days with entries |
| **Dark Mode** | System-default, force light, or force dark -- your choice, persisted across restarts |
| **Offline-First** | 100% local storage. Zero network calls. Zero permissions beyond camera and storage |
| **Data Safety** | Entries stored in Room (SQLite). Photos stored in app-internal storage via FileProvider |

---

## Screenshots

> Screenshots will be added after the first stable release.

---

## Tech Stack

<div align="center">

| Layer | Technology |
|:---:|:---:|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Database | Room (SQLite) |
| Dependency Injection | Hilt |
| Navigation | Navigation Compose |
| Preferences | DataStore |
| Image Loading | Coil |
| Logging | Timber |

</div>

---

## Architecture

The app follows a **single-activity, MVVM** architecture with a clean separation of concerns across four layers:

```
UI Layer (Compose Screens)
    |
    |  observes StateFlow via collectAsStateWithLifecycle
    v
ViewModel Layer (feature-scoped)
    |
    |  calls suspend functions and collects Flow
    v
Repository Layer (interface + implementation)
    |
    |  queries and writes
    v
Data Layer (Room DAOs --> SQLite)
```

**Key architectural decisions:**

- **Unidirectional data flow** -- UI observes state, dispatches events to ViewModel, ViewModel delegates to Repository.
- **Domain models** are separated from Room entities to keep the UI layer decoupled from the database schema.
- **Hilt** manages the full dependency graph from database singletons down to ViewModel injection.
- **DataStore** handles lightweight key-value preferences (dark mode) independently of Room.

---

## Getting Started

### Prerequisites

- **Android Studio** Ladybug (2024.2.1) or later
- **JDK** 17 or later
- **Android SDK** with API level 26 (minimum) and API level 35 (target)
- A physical device or emulator running Android 8.0+

### Clone the Repository

```bash
git clone https://github.com/your-username/mydairy_app.git
cd mydairy_app
```

---

## Build and Run

1. Open the project in **Android Studio**.
2. Let Gradle sync complete. All dependencies are declared in `libs.versions.toml`.
3. Select a device or emulator from the device dropdown.
4. Click **Run** or use the shortcut:

```
Shift + F10    (Windows / Linux)
Ctrl  + R      (macOS)
```

### Build Variants

| Variant | Purpose |
|:---|:---|
| `debug` | Development builds with logging enabled via Timber |
| `release` | Minified with R8, ProGuard rules applied for Room, Hilt, and Coil |

---

## Performance Targets

| Metric | Target |
|:---|:---|
| Cold start | < 1.5 seconds |
| Entry save | < 300 milliseconds |
| Search response | < 200 milliseconds (up to 10,000 entries) |
| Photo thumbnail load | < 500 milliseconds (cached) |
| Network dependency | None -- fully offline |

---

## Contributing

Contributions are welcome. Please follow these steps:

1. **Fork** the repository.
2. **Create a feature branch** from `main`:

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Commit** your changes with clear, descriptive messages.
4. **Push** to your fork and open a **Pull Request**.

Please make sure your code:

- Follows the existing project structure and naming conventions
- Compiles without warnings
- Does not introduce any new dependencies without discussion

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
