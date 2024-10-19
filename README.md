**StressMeter**

StressMeter is an Android application designed to help users track and visualize their stress levels by selecting from a grid of images. The app stores the user's selections along with timestamps in a CSV file and displays a graph visualizing the data over time.

Features

MVVM Architecture: Clean separation of concerns between UI, data handling, and business logic.

Image Selection: Users select an image that represents their stress level from a set of randomized images.

CSV Data Storage: Stores user selections and timestamps in a CSV file for future reference.

Data Visualization: Displays stress data as graphs, helping users visualize their stress patterns over time.

Fragment-based Navigation: Supports seamless navigation between different screens (stress meter, results) using Android Navigation components.

Background Vibration: Vibrates the phone upon app launch to improve user experience.

Semi-transparent UI: Implements semi-transparent backgrounds for a polished and visually appealing design.

Error Handling: Includes error handling for file operations, ensuring robustness and stability.


**Installation**

To run this project on your local machine:

Clone the repository:
git clone [https://github.com/karanjhand/Stress-Meter.git](https://github.com/karanjhand/Stress-Meter)

Open the project in Android Studio, using the folder named Karanbir_Singh_Stress_Meter
Build and run the project on an Android device or emulator.

Requirements

Android SDK 24 or higher.
Android Studio (latest version recommended).
Gradle (comes with Android Studio).

Usage

Select the image that best represents your stress level.
Tap on "More Images" to refresh the image grid with new images.
Your selected image's stress score and timestamp will be stored in a CSV file.
View your stress patterns over time using the results screen with graphical representation and tabular data.

Tech Stack

Kotlin: The primary programming language for the app.
MVVM Architecture: For clean separation of concerns and maintainable code.
Android Navigation Components: For seamless navigation between fragments.
CSV File Storage: For saving stress data and timestamps.
MPAndroidChart (or other library): For visualizing stress data in graph format.



