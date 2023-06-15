import { useState } from "react";
import $ from 'jquery';


function SendBox({sendMessage }) {
    const [message, setMessage] = useState("");
    const handleTextChange = (event) => {
        setMessage(event.target.value);
    }

    const send = async (event) => {
        if(!message)
            return;
        sendMessage(message);
        $("#send").val("");
        setMessage("")
      };

    const nextVersion = (event) => {
        alert("This feature is only for premium users right now..");
    }

  


    return (
        <div id="send_message">
            <input type="text"  onChange={handleTextChange} className="form-control rounded-0" id="send" aria-describedby="emailHelp" placeholder="Enter your message here..." />
            <button type="button" className="btn btn-outline-light btn-sm textColor" id="send_btn" onClick={send}>
                Send
            </button>
            <button type="button" className="btn btn-outline-light btn-sm textColor" id="send_btn" disabled onClick={nextVersion}>
                Attach A File  <small>(this feature will be available in the next update)</small>
            </button>
        </div>
    )
}
export default SendBox;