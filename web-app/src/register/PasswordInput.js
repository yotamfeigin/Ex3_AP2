import $, { event } from 'jquery';
import React, { useState } from 'react';
import {validatePassword} from './functions';



function PasswordInput({ className, id, password, setPassword, vPassword, setvPassword,  setIsPasswordValid }) {


    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handlePasswordFocus = (event) => {
        $('#password-rules').text("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
    }

    const handlePasswordBlur = (event) => {

        if (validatePassword(password) == 'fine') {
            $('#' + id).css('border', '3px solid green');
            $('#password-rules').text("");
            setIsPasswordValid(true);
        } else if (validatePassword(password) == 'length') {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must be at least 8 characters long");
            setIsPasswordValid(false);
        } else if (validatePassword(password) == 'upper') {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must contain at least one uppercase letter");
            setIsPasswordValid(false);
        } else if (validatePassword(password) == 'lower') {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must contain at least one lowercase letter");
            setIsPasswordValid(false);
        } else if (validatePassword(password) == 'digit') {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must contain at least one digit");
            setIsPasswordValid(false);
        } else if (validatePassword(password) == 'special') {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must contain at least one special character");
            setIsPasswordValid(false);
        }
        else {
            $('#' + id).css('border', '3px solid red');
            $('#password-rules').text("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            setIsPasswordValid(false);
        }

    }

    const handleValidChange = (event) => {
        setvPassword(event.target.value);
    }

    const handleValidFocus = (event) => {
        $('#vPassword-rules').text("Re-enter the same password.")
    }

    const handleValidBlur = () => {
        if (password == vPassword) {
            if (validatePassword(password) == 'fine') {
                $('#vPassword-rules').text("");
                $('#' + id).css('border', '3px solid green');
            } else {
                $('#vPassword-rules').text("");
                $('#' + id).css('border', '3px solid red');
            }
        } else {
            $('#vPassword-rules').text("Passwords are not the same!");
            $('#' + id).css('border', '3px solid red');
        }
    }

    if (className == "password") {
        return (
            <div className="form-group">
                <label htmlFor="password" className="textColor">
                    Password
                </label>
                <div id="password-rules" className="textColor"></div>
                <input
                    type="password"
                    name={id}
                    className="form-control password"
                    id={id}
                    placeholder="Enter Password"
                    required
                    pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$"
                    title="Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
                    onChange={handlePasswordChange}
                    onFocus={handlePasswordFocus}
                    onBlur={handlePasswordBlur}
                />
            </div>
        )
    } else if (className == "password_validation") {
        return (
            <div className="form-group">
                <label htmlFor={className}
                    className="textColor">
                    Re-enter Password:
                </label>
                <div id="vPassword-rules" className="textColor"></div>
                <input type="password"
                    name={id}
                    className="form-control password_validation"
                    id={id}
                    placeholder="Re-enter Password:"
                    required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$"
                    title="Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
                    onChange={handleValidChange}
                    onFocus={handleValidFocus}
                    onBlur={handleValidBlur} />

            </div>
        )
    } else if (className == "password-login") {
        return (
            <>
                <label htmlFor="password"
                    className="textColor">
                    Password:
                </label>
                <input type="password"
                    className="form-control password-login"
                    id="password" name="password"
                    placeholder="Password"
                    required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$"
                    title="Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
                    onChange={handlePasswordChange}
                />
                <small id="exampleInputPassword1" className=" textColor">We'll never share your Password with anyone else.</small>
            </>
        )
    }
}
export default PasswordInput;