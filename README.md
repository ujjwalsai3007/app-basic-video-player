# Basic Video Player with PiP

A simple video player Android application that demonstrates proper MVVM architecture implementation with Picture-in-Picture (PiP) functionality and dynamic video source loading from a JSON file.

## Features

- **Picture-in-Picture Mode**: Continue watching videos in a small floating window when you navigate away from the app
- **Dynamic Video Source**: Video URL is loaded from a local JSON file
- **MVVM Architecture**: Clean separation of concerns with Model-View-ViewModel pattern
- **Error Handling**: Graceful fallback to local video when remote loading fails
- **Dependency Injection**: Uses Hilt for clean dependency management

## Screenshots

| Full Screen Playback | Picture-in-Picture Mode |
|:---:|:---:|
| ![Full Screen](https://github.com/user-attachments/assets/5dffb438-651b-4462-93a7-4fc94e81ab86) | ![PiP Mode](https://github.com/user-attachments/assets/3449e856-b31c-4ab1-a8f1-890a33e78fd2) |

## Implementation Details

### Architecture Components

- **Model**: Handles data operations through JsonDataSource and VideoRepository
- **View**: UI components in VideoPlayerActivity with lifecycle-aware playback
- **ViewModel**: Manages business logic and UI state in VideoPlayerViewModel

### Libraries Used

- ExoPlayer for video playback
- Hilt for dependency injection
- Gson for JSON parsing
- Android Lifecycle components for MVVM implementation
- Kotlin Coroutines for asynchronous operations

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Run the app on an emulator or physical device running Android 8.0 (API level 26) or higher for full PiP support

## How It Works

The app reads a video URL from a JSON file in the assets folder and plays it using ExoPlayer. When you press back or navigate away from the app, it automatically enters Picture-in-Picture mode, allowing you to continue watching the video while using other apps.

If there's any issue loading the video from the remote URL, the app will automatically fall back to a local video resource.

## Requirements

- Android 8.0 (API level 26) or higher for PiP functionality
- Android Studio Arctic Fox or newer
