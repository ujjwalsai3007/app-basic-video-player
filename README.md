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
| <img src="https://github.com/user-attachments/assets/7dc2418d-8553-48ae-a404-f95c4aadf805" width="300"/> | <img src="https://github.com/user-attachments/assets/06dd6619-01e4-4b90-b65b-d796e4288241" width="300"/> |



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

### Architecture Flow

1. **Data Loading**: When the app starts, the `JsonDataSource` reads the video URL from the assets folder and passes it to the `VideoRepository`.

2. **Repository Layer**: The `VideoRepository` processes the data and handles any potential errors, ensuring a valid URL is always provided.

3. **ViewModel Processing**: The `VideoPlayerViewModel` requests the URL from the repository and exposes it to the UI through LiveData, along with loading states.

4. **UI Rendering**: The `VideoPlayerActivity` observes the ViewModel's LiveData and updates the UI accordingly, loading the video when the URL is available.

5. **Video Playback**: ExoPlayer handles the actual video playback, with proper lifecycle management to prevent memory leaks.

6. **PiP Functionality**: The `PipHelper` class manages transitions to and from Picture-in-Picture mode, maintaining the playback state during transitions.

### Error Handling

The app implements multiple layers of error handling to ensure a smooth user experience:

- JSON parsing errors are caught in the data source
- Network errors are handled when loading the video
- Playback errors trigger fallback to local video
- UI states reflect loading progress and success states

This multi-layered approach ensures that users always have a working video player experience, even when offline or when the remote video source is unavailable.

## Requirements

- Android 8.0 (API level 26) or higher for PiP functionality
- Android Studio Arctic Fox or newer
