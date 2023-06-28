import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import TextInput from '../register/TextInput';
import PasswordInput from '../register/PasswordInput';


function LoginPage({ setLoginStatus, setCurrentUser, setToken, setContacts}) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  let newtoken = "";
  

  const navigate = useNavigate();

  const getChats = async (event) => {
    const newChatList = await fetch(`http://localhost:5000/api/Chats`, {
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${newtoken}`
      }
    })
    const chatJson = await newChatList.json();
    setContacts(chatJson);
  }

  const login = async (event) => {
    var UserLogin = {
      "username": username,
      "password": password
    };
  
    const res = await fetch('http://localhost:5000/api/Tokens', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(UserLogin)
    });
  
    if (res.ok) {
      newtoken = await res.text();
      // Use the token here
      
  
      const getUserResponse = await fetch(`http://localhost:5000/api/Users/${username}`, {
        method: 'get',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${newtoken}`
        }
      });
  
      if (getUserResponse.ok) {
        const userJson = await getUserResponse.json();
        // Extract the values from the JSON response
        const { username, displayName, profilePic } = userJson;
        // Use the extracted values
        setToken(newtoken);
        setCurrentUser(userJson);
        setLoginStatus(true);
        getChats(newtoken);
        navigate('/chat');
        
      } else {
        // Handle error cases for GET request
        alert('Error:', getUserResponse.status);
      }
    } else {
      // Handle error cases for POST request
      alert('Username or password are wrong', res.status);
    }
  }

  return (
    <main>
      <div className="login">
        <form className="login">
          <TextInput className="username-login"   username={username} setUsername={setUsername} />
          <div className="form-group">
            <PasswordInput className="password-login" password={password} setPassword={setPassword} />
          </div>
          <button type="button" className="btn btn-outline-light btn-sm textColor" onClick={login}>Login</button> <br />
          <small className="textColor">Don't have an account yet? <Link className="textColor" to="/register">Register</Link></small>
        </form>

      </div>
    </main>


  );
}


export default LoginPage;
