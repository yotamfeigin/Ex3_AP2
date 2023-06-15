# Chat Website
![Chat Website Logo](https://user-images.githubusercontent.com/71657634/234795252-4abe6167-68e9-4096-a828-ef4e30b7c388.jpg)

Welcome to the Chat Website project! This is a simple chat application built using React and Express.js. It allows users to register, log in, and send messages to other users in real-time.

## Getting Started

To run the Chat Website project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/nitay510/ex2_mitkadem2.git `
2. Navigate to the project directory: `cd ex2_mitkadem2` *(You could also do `cd ex2_mitkadem2\web-server` and skip 3)*
3. Once you're in the directory, navigate to the server directory: `cd web-server`
4. Install dependencies: Run `npm install` to install the necessary dependencies.
5. Start the server: Run `node server.js` to start the server.
6. Open the website: Once the server is running, access the chat website by going to `http://localhost:5000` in your browser.

## Side-note
Before we used to run the react-app on it's own and it'd communicate with the server (which was running on it's own as well), Then we added the 'build' directory (that we got by running `npm run build` on the web-app) to the server. So now you can just run the server and get the react app from the build folder





## Usage

- Register: Create a new account by providing a username and password.
- Log in: Use your registered credentials to log in.
- Send messages: Once logged in, you can send messages to other users in real-time.

Please note that the chat functionality is powered by Socket.io, which provides instant updates when a new message is received or a conversation is deleted/ created.
This enables real-time communication between users.

The application uses MongoDB to store and retrieve chat data. Messages and user information are saved in a MongoDB database for persistence.

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.
