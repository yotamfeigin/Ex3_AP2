# Chat Website
![Chat Website Logo](https://user-images.githubusercontent.com/71657634/234795252-4abe6167-68e9-4096-a828-ef4e30b7c388.jpg)

Welcome to the Chat Website project! This is a simple chat application built using React, Express.js, and Android Studio. It allows users to register, log in, and send messages to other users in real-time using both the web and Android interfaces.

## Getting Started

To run the Chat Website project locally, follow these steps:

Clone the repository: git clone https://github.com/nitay510/ex2_mitkadem2.git
Navigate to the project directory: cd EX3_AP2
Install dependencies:
   - For the Web App:
        Navigate to the web-server directory: cd web-server
        Run npm install to install the necessary dependencies.
   - For the Android App:
        Open the android-app directory in Android Studio.
        Build and run the app using an Android emulator or connect your own Android device.

## Running the Server

Start the server:
   - For the Web App: In the web-server directory, run node server.js to start the server.
   - For the Android App: Before running the Android app, make sure you have the server running by following these steps:
         1.In the web-server directory, run node server.js to start the server.
         2.Make a note of the server address (e.g., http://localhost:5000).

## Accessing the Apps

   - Web App: Once the server is running, access the chat website by going to http://localhost:5000 in your browser.
   - Android App: Use the Android Studio emulator or install the app on your own Android device to run the Android app.

## Configuring Server Address

    The Android app allows you to configure the server address. By default, the app is configured to connect to the server running on http://10.0.2.2:5000, which is    suitable for running the app on the Android emulator. If you are running the server on a different address or using a physical Android device, follow these steps to update the configuration:

  -   Android App: After launching the app, navigate to the Settings screen. In the Settings, you can modify the server address by entering the appropriate IP address  and port number. Once you save the new configuration, the app will connect to the specified server address.

## Firebase Cloud Messaging (FCM) Setup

    To enable push notifications in the Android app, you need to set up Firebase Cloud Messaging (FCM). Follow these steps to configure FCM:

   - Create a new Firebase project in the Firebase Console.
    Set up the Android app:
        In the Firebase project settings, add an Android app and follow the instructions to download the google-services.json file.
        Copy the google-services.json file into the android-app/app/ directory of the project.
        Update the package value in the google-services.json file to match your Android app's package name.
        Sync the project with the updated google-services.json file in Android Studio.
        Obtain the server key:
            In the Firebase project settings, go to the "Cloud Messaging" tab.
            Copy the "Server Key" value.
            Configure the server:
        In the web-server directory, open the firebaseConfig.json file.
        Replace the placeholder value with your Firebase project's server key.
        Save the file.

## Usage

- Register: Create a new account by providing a username and password.
- Log in: Use your registered credentials to log in.
- Send messages: Once logged in, you can send messages to other users in real-time.

Please note that the chat functionality is powered by Socket.io, which provides instant updates when a new message is received or a conversation is deleted/ created.
This enables real-time communication between users.

The application uses MongoDB to store and retrieve chat data. Messages and user information are saved in a MongoDB database for persistence.

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.
