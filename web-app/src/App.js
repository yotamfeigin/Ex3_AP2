import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './colors.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import LoginPage from './login/LoginPage';
import RegisterPage from './register/RegisterPage';
import ChatPage from './chat/ChatPage';

function App() {
  const [loginStatus, setLoginStatus] = useState(false);
  const [currentUser, setCurrentUser] = useState([]);
  const [contacts, setContacts] = useState([]);
  const [currentChat, setCurrentChat] = useState([]);
  const [token, setToken] = useState(' ');
  const [messages, setMessages] = useState([]);
  const [chatId, setChatId] = useState();



  return (
    <BrowserRouter>
      <Routes>
        <Route
          path='/'
          element={
            <LoginPage
              setLoginStatus={setLoginStatus}
              setCurrentUser={setCurrentUser}
              setToken={setToken}
              setContacts={setContacts}
            />
          }
        />
        <Route path='/register' element={<RegisterPage />} />
        <Route
          path='/chat'
          element={
            <ChatPage
              currentChat={currentChat}
              setCurrentChat={setCurrentChat}
              contacts={contacts}
              setContacts={setContacts}
              loginStatus={loginStatus}
              setLoginStatus={setLoginStatus}
              currentUser={currentUser}
              token={token}
              messages={messages}
              setMessages={setMessages}
              chatId={chatId}
              setChatId={setChatId}
            />
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
