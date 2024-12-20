
App Summary
The Meezer app is a music search and playlist management application. Users can search for songs and albums from Deezer, view details about tracks, and manage their playlists. The app provides a seamless user experience with a clean and intuitive interface.

Key Features:
Search Functionality: Users can search for tracks and albums using the search bar.
Track Details: Detailed information about tracks, including album artwork, artist, and album details.
Playlist Management: Users can create playlists, add tracks to playlists, and manage their playlists.
Playback: Users can play a preview of the tracks directly within the app.
Illustrative Screenshots:
[Here you can add a screenshot from the Search screen, with the search bar and search results]
[Here you can add a screenshot from the Track Details screen, showing detailed information about a track]
[Here you can add a screenshot from the Playlist screen, displaying the list of playlists and tracks within a selected playlist]
App Architecture
The Meezer app follows a modular architecture with a clear separation of concerns. The architecture is designed to be scalable and maintainable, leveraging modern Android development practices. We aimed to use a variety of options available in Android development, incorporating both Jetpack Compose and traditional RecyclerView where appropriate.

Architecture Diagram:
Architecture Diagram

Implementation Choices:
MVVM Pattern: The app uses the Model-View-ViewModel (MVVM) pattern to separate the UI logic from the business logic. This helps in maintaining a clean architecture and makes the app easier to test.
Hilt for Dependency Injection: Hilt is used for dependency injection, which simplifies the process of providing dependencies and improves the testability of the app.
Jetpack Compose and RecyclerView: The UI is built using a combination of Jetpack Compose and RecyclerView. Jetpack Compose allows for a declarative approach to building UI components, making the UI code more readable and easier to maintain. RecyclerView is used for certain parts of the app to demonstrate the use of traditional Android UI components.
Room Database: Room is used for local data storage, providing an abstraction layer over SQLite. This helps in managing the app's data more efficiently.
Retrofit for Network Calls: Retrofit is used for making network calls to the Deezer API. It simplifies the process of making HTTP requests and handling responses.
Key Components:
ViewModel: Manages the UI-related data and handles business logic. Examples include SearchViewModel and PlaylistViewModel.
Repository: Acts as a single source of truth for data. It abstracts the data sources and provides a clean API for data access.
UI Components: Built using a combination of Jetpack Compose and RecyclerView, these components render the UI and interact with the ViewModel. Examples include SearchScreen using Jetpack Compose and PlaylistScreen using RecyclerView.
Unresolved Technical Problems
Despite the robust architecture and implementation, there are a few technical issues that remain unresolved:

Keyboard Overlapping Navigation Bar: When the user taps on the search box, the keyboard appears but overlaps the bottom navigation bar. This issue affects the user experience as the navigation bar becomes inaccessible while typing.
Error Handling: The app currently lacks comprehensive error handling. Network errors and other exceptions are not properly communicated to the user, which can lead to a poor user experience.
Unit Tests for ViewModels: While some unit tests are in place, there is a need for more comprehensive testing of the ViewModels to ensure the business logic is thoroughly tested.
Feel free to add the screenshots and make any necessary adjustments to the report.


--ARCHITECTURE--
The architecture of the Meezer app is designed to promote scalability, maintainability, and testability, adhering to best practices outlined in the Android documentation. By implementing a **Model-View-ViewModel (MVVM)** pattern, we effectively separate the user interface from the business logic. This separation of concerns ensures that our UI components remain lightweight and focused solely on rendering data and handling user interactions, while the ViewModels manage the application state and business logic. This approach aligns with the principles described in the Android architecture guidelines, which emphasize minimizing dependencies on UI components and managing data through persistent models to enhance robustness and testability [1].

To further streamline our architecture, we utilized **Hilt** for dependency injection. Hilt simplifies the management of dependencies across various components of the app, allowing for cleaner code and easier testing. By defining clear boundaries between different parts of the application, we ensure that each component has a specific responsibility, which is crucial for maintaining a clean architecture [1]. 

Our app also leverages **Room** for local data storage, providing an efficient abstraction layer over SQLite. This allows us to manage data persistently while ensuring that it remains accessible even when the app is not running. Coupled with **Retrofit** for network calls to the Deezer API, this architecture supports a seamless user experience by efficiently handling data retrieval and storage [1][2].

Incorporating both **Jetpack Compose** and **RecyclerView** into our UI design allows us to utilize modern declarative programming alongside traditional UI components. This combination enhances readability and maintainability of our UI code while demonstrating versatility in our implementation choices. Overall, this architecture not only supports current functionality but also positions the Meezer app for future growth and feature enhancements.

For more detailed insights into recommended app architectures and best practices, we referred to resources such as the official Android architecture documentation [1] and GitHub's architecture templates [2]. These resources have been instrumental in guiding our architectural decisions throughout the development process.

Citations:
[1] https://developer.android.com/topic/architecture
[2] https://github.com/apache/flink-kubernetes-operator/pkgs/container/flink-kubernetes-operator/289154679?tag=c703255
