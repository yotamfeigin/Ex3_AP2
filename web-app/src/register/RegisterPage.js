import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import PasswordInput from "./PasswordInput";
import PreviewImage from "./PreviewImage";
import TextInput from "./TextInput";
function RegisterPage(){

  function isValidName(name) {
    const nameRegex = /^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$/;
    return nameRegex.test(name);
  }
  const [password, setPassword] = useState('');
  const [isUsernameValid, setIsUsernameValid] = useState(false);
  const [isPasswordValid, setIsPasswordValid] = useState(false);
  const [vPassword, setvPassword] = useState('');
  const [username, setUsername] = useState('');
  const [previewImg, setPreviewImg] = useState("/logo.jpg");
  const [displayName, setDisplayName] = useState("");

  const navigate = useNavigate();

    const handleRegister = async (event) =>{
        if(!isUsernameValid){
          alert("Username is not valid!");
          return;
        }
        
        if(!isPasswordValid){
          alert("Password is not valid!");
          return;
        }
        if(password != vPassword){
          alert("Passwords are not the same!");
          return;
        }
        if(!isValidName(displayName)){
          alert("You must have a display name!")
          return;
        }
        var newUser = {
          "username":username,
           "password":password,
           "displayName":displayName,
           "profilePic":previewImg,
          };
          const res = await fetch('http://localhost:5000/api/Users', {
            'method': 'post', // send a post request
            'headers': {
            'Content-Type': 'application/json', // the data (username/password) is in the form of a JSON object
            },
            'body': JSON.stringify(newUser) // The actual data (username/password)
            }
            )
            if (res.ok) {
             navigate("/");
            } else {
              // Handle error cases
              alert("username or password are wrong")
            }
           
    }


    return (
        <main>
        <form className="login" >
          <TextInput className="username-class" id="username" username={username} setUsername={setUsername} setIsUsernameValid={setIsUsernameValid} />
          <br/>
          <PasswordInput className="password" id="password" password={password} setPassword={setPassword} isPasswordValid={isPasswordValid} setIsPasswordValid={setIsPasswordValid} />
          <br/>
          <PasswordInput className="password_validation" id="password_validation" password={password} setPassword={setPassword} vPassword={vPassword} setvPassword={setvPassword} />
          <br/>
          <TextInput className="display-name-class" id="display-name" displayName={displayName} setDisplayName={setDisplayName} />
          <br/>
          <PreviewImage previewImg={previewImg} setPreviewImg={setPreviewImg} />
          <br/>
          <button type="button" className="btn btn-outline-light btn-sm textColor" id="register_button" onClick={handleRegister}>Register</button> <br />      
        </form>
        
      </main>
    );
}

export default RegisterPage;