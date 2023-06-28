import $, { event } from 'jquery';


function isValidUsername(input) {
    // Check if input is at least 4 characters long
    if (input.length < 4) {
        return 'short';
    }

    // Check if input contains only digits and letters
    const regex = /^[a-zA-Z0-9]+$/;
    if (!regex.test(input)) {
        return 'char';
    }
    

    // If input passes both checks, return true
    return 'fine';
}

function isValidName(name) {
    const nameRegex = /^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$/;
    return nameRegex.test(name);
}

function TextInput({ className, id, username, setUsername, displayName, setDisplayName, setIsUsernameValid }) {
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }

    const handleUsernameFocus = (event) => {
        $('#username-rules').text("Username must be 4-20 characters and can only contain letters and numbers.");
    }

    const handleUsernameBlur = (event) => {
        var status = isValidUsername(username);
        if (status == 'fine') {
            $('#username-rules').text("");
            $('#' + id).css('border', '3px solid green');
            setIsUsernameValid(true);
        } else if (status == 'short') {
            $('#username-rules').text("Username is too short, please add characters");
            $('#' + id).css('border', '3px solid red');
            setIsUsernameValid(false);
        } else if (status == 'char') {
            $('#username-rules').text("Username can only contain letters and numbers");
            $('#' + id).css('border', '3px solid red');
            setIsUsernameValid(false);
        }
        else if (status == 'taken') {
            $('#username-rules').text("Username is already taken");
            $('#' + id).css('border', '3px solid red');
            setIsUsernameValid(false);
        }

    }

    const handleDisplayNameChange = (event) => {
        setDisplayName(event.target.value);
    }

    const handleDisplayNameFocus = (event) => {
        $('#displayName-rules').text("This is the name to represent you");
    }

    const handleDisplayNameBlur = (event) => {
        var status = isValidName(displayName);
        if (status == true) {
            $('#' + id).css('border', '3px solid green');
            $('#displayName-rules').text("");
        } else {
            $('#' + id).css('border', '3px solid red');
            $('#displayName-rules').text("Not a valid name!");
        }
    }

    
    if (className == 'username-class') {
        return (
            <div
                className="form-group">
                <label
                    htmlFor="username"
                    className="textColor">
                    Username:
                </label>
                <div id="username-rules" className="textColor" ></div>
                <input
                    type="text"
                    className="form-control username-class"
                    name={id}
                    id={id}
                    placeholder="Enter Username"
                    required pattern="[a-zA-Z0-9]{4,20}"
                    title="Username must be 4-20 characters and can only contain letters and numbers."
                    onChange={handleUsernameChange}
                    onFocus={handleUsernameFocus}
                    onBlur={handleUsernameBlur} />
            </div>
        )
    } else if (className == 'display-name-class') {
        return (
            <div className="form-group">
                <label
                    htmlFor="display_name"
                    className="textColor">
                    Display Name:
                </label>
                <div id="displayName-rules" className="textColor" ></div>
                <input type="text"
                    name={id}
                    className="form-control display-name-class"
                    id={id}
                    aria-describedby="emailHelp"
                    placeholder="Enter Display Name"
                    required pattern="[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*{4,20}"
                    title="Username must be 4-20 characters and can only contain letters and numbers."
                    onChange={handleDisplayNameChange}
                    onFocus={handleDisplayNameFocus}
                    onBlur={handleDisplayNameBlur}
                />
            </div>
        );
    } else if(className == 'username-login') {
        return(
            <div className="form-group">
              <label htmlFor="username" 
              className="textColor">
                Username:
                </label>
              <input type="text" 
              className="form-control username-login" 
              id="username" name="username" 
              aria-describedby="emailHelp" 
              placeholder="Enter username" 
              required pattern="[a-zA-Z0-9]{4,20}" 
              title="Username must be 4-20 characters and can only contain letters and numbers." 
              onChange={handleUsernameChange}/>
            </div>
        )
        
    }

}
export default TextInput;