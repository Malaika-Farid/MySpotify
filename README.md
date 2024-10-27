# MySpotify – A Spotify Clone

MySpotify is a simplified clone of Spotify, created to replicate core features of the popular music streaming app. It allows users to experience functionalities like account creation, logging in, and accessing songs and playlists with a clean and modern UI.

## Project Overview

MySpotify aims to replicate the UI/UX of Spotify with features like:

- User authentication (login, sign-up pages).
- Eye-catching dark-themed design.
- Smooth navigation with interactive icons.
- Password visibility toggle and input fields for user details.
- Responsive layout to ensure a seamless experience on different screen sizes.

This project focuses on UI design, navigation, and front-end logic using Kotlin and Android Studio.

## Setup Instructions

Clone the repository to your local machine:
https://github.com/Malaika-Farid/MySpotify/tree/MySpotify_Evaluation_03/app/src

1. Open the project in Android Studio.
2. Ensure Git is installed and properly configured on your machine.
3. Sync the project with Gradle by clicking **Sync Now** in Android Studio.
4. Run the app on an emulator or a physical device:
    - Connect a device or set up an Android Virtual Device (AVD).
    - Click **Run > Run 'app'** or press **Shift + F10**.
5. If any dependencies are missing, install them using the SDK Manager in Android Studio.

## Screens Designed and Their Purpose

1. **Sign-Up Screen**  
   **Purpose:** Allow users to create a new account by entering their email.  
   **Features:**
    - Back arrow to navigate to the previous page.
    - Input field for email with subtext informing about email confirmation.
    - Next button to proceed further.

2. **Login Screen**  
   **Purpose:** Let users log in using their email or username and password.  
   **Features:**
    - Password visibility toggle for convenience.
    - "Log in without password" option as an alternative method.
    - Interactive and minimalistic UI with dark theme elements.

3. **Splash Screen**  
   **Purpose:** To show the start screen with the logo of Spotify.

4. **SignUp Free Screen**  
   **Purpose:** To input the email from the user and to confirm this email.

## Technical Challenges Faced

- Implementing the back button using `finish()` without causing unintended navigation loops.
- Ensuring smooth switching between hidden and visible password states without disrupting the layout.
- Properly configuring `.gitignore` to avoid uploading unnecessary files like Gradle and build folders.
- Achieving a uniform design with consistent paddings, colors, and button styles across multiple screens.

## Future Plans

- Add a home page to show playlists and recently played tracks.
- Develop a profile section where users can change personal details and configure app settings.
- Incorporate the Spotify Web API to fetch real-time playlists and song data.
- Enable streaming functionality for a complete Spotify experience.

Feel free to fork this repository and contribute! If you encounter any issues, raise them in the **Issues** section or submit a **Pull Request** with your improvements.
