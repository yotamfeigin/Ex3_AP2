import { useEffect, useState, useRef  } from 'react';
import { useNavigate } from 'react-router-dom';
import Contact from './Contact';
import BubbleChat from './BubbleChat';
import MyProfile from './MyProfile';
import SendBox from './SendBox';
import io from 'socket.io-client';
import $ from 'jquery';

const socket = io("http://localhost:5000");


function ChatPage({
  loginStatus,
  setLoginStatus,
  currentUser,
  contacts,
  setContacts,
  currentChat,
  setCurrentChat,
  token,
  messages,
  setMessages,
  chatId,
  setChatId,
}) {
  const [currentPicture, setCurrentPicture] = useState('logo.jpg');
  const navigate = useNavigate();

  useEffect(() => {
    if (loginStatus === false) {
      navigate('/');
    }

    const connectSocket = async () => {
      // Connect to the Socket.IO server
      socket.connect();

      // Handle the 'newMessage' event
      socket.on('newMessage', async (data) => {
        if (chatId === data) {
          await getMessages();
        }
        await getChats();
      });

      socket.on('newContact', async () => {
        await getChats();
      });

      socket.on('deleteContact', async () => {
        await getChats();
      });
    };

    connectSocket();

    // Cleanup the socket connection on component unmount
    return () => {
      if (socket) {
        socket.disconnect();
      }
    };
  }, [loginStatus, navigate, chatId]);

  const chatBoxRef = useRef(null);

  useEffect(() => {
    // Scroll the chat_box element to the bottom
    chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
  }, [messages]);



  const getMessages = async event => {
    const res = await fetch(
      `http://localhost:5000/api/Chats/${chatId}/Messages`,
      {
        method: 'get',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        }
      }
    )

    if (res.ok) {
      const messages = await res.json()
      setMessages(messages)
    } else {
      alert('Unable to fetch messages')
    }
  }

  const getChats = async event => {
    const newChatList = await fetch(`http://localhost:5000/api/Chats`, {
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if(newChatList.ok){
    const chatJson = await newChatList.json()
    setContacts(chatJson)
    }
  }

  const logOut = event => {
    setCurrentChat('')
    setCurrentPicture('')
    setContacts([])
    setLoginStatus(false)
    setMessages([])
    setChatId('')
    if (socket) {
      socket.disconnect()
    }
    navigate('/')
  }

  const addContact = async name => {
    if(name == currentUser.username){
      alert('You cannot add yourself')
      $('#contactName').val('')
      return
    }
    const newContact = {
      username: name
    }
    const res = await fetch('http://localhost:5000/api/Chats', {
      method: 'post', // send a post request
      headers: {
        'Content-Type': 'application/json', // the data (username/password) is in the form of a JSON object
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(newContact) // The actual data (username/password)
    })
    if (res.ok) {
      socket.emit('newContact')
      await getChats()
    } else {
      // Handle error cases
      alert('no such username')
    }
    $('#contactName').val('')

  }

  const handleSendMessage = async message => {
    if(chatId == ''){
      alert('Please select a chat')
      return
    }
    const newMessage = {
      msg: message
    }
    const res = await fetch(
      `http://localhost:5000/api/Chats/${chatId}/Messages`,
      {
        method: 'post',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(newMessage)
      }
    )

    if (res.ok) {
      await socket.emit('newMessage', chatId)
      await getMessages()
      await getChats()
    } else {
      alert('Unable to send message')
    }
  }

  return (
    <>
      <button
        type='button'
        className='btn btn-outline-light btn-sm textColor'
        id='logout'
        onClick={logOut}
      >
        Logout
      </button>

      <main id='main_chat'>
        <div id='contacts' className='column'>
          <MyProfile
            picture={currentUser.profilePic}
            name={currentUser.displayName}
          />
          <div id='contacts-list'>
            {contacts && contacts.map((contact, index) => (
              <Contact
                key={contact.id}
                id={contact.id}
                picture={contact.user.profilePic}
                name={contact.user.displayName}
                lastMessage={contact.lastMessage}
                setCurrentChat={setCurrentChat}
                setCurrentPicture={setCurrentPicture}
                token={token}
                currentUser={currentUser}
                setMessages={setMessages}
                setChatId={setChatId}
                chatId={chatId}
                setContacts={setContacts}
                socket={socket}
              />
            ))}
          </div>

          <button
            className='btn btn-primary'
            id='add_contact_button'
            data-toggle='modal'
            data-target='#addContactModal'
          >
            Add Contact
          </button>

          <div
            className='modal fade'
            id='addContactModal'
            tabIndex='-1'
            role='dialog'
            aria-labelledby='addContactModalLabel'
            aria-hidden='true'
          >
            <div className='modal-dialog' role='document'>
              <div className='modal-content'>
                <div className='modal-header'>
                  <h5 className='modal-title' id='addContactModalLabel'>
                    Add Contact
                  </h5>
                  <button
                    type='button'
                    className='close'
                    data-dismiss='modal'
                    aria-label='Close'
                  >
                    <span aria-hidden='true'>&times;</span>
                  </button>
                </div>
                <div className='modal-body'>
                  <form>
                    <div className='form-group'>
                      <label htmlFor='contactName'>Name</label>
                      <input
                        type='text'
                        className='form-control'
                        id='contactName'
                        placeholder='Enter contact nickname'
                      />
                    </div>
                  </form>
                </div>
                <div className='modal-footer'>
                  <button
                    type='button'
                    className='btn btn-secondary'
                    data-dismiss='modal'
                  >
                    Close
                  </button>
                  <button
                    type='button'
                    className='btn btn-primary'
                    onClick={() =>
                      addContact(document.getElementById('contactName').value)
                    }
                    data-dismiss='modal'
                  >
                    Add
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id='chat'>
          <div id='chat_header'>
            <img
              src={currentPicture}
              alt='profile picture'
              id='chat_profile_picture_2'
            />
            <b className='textColor contatcs_chat'>{currentChat}</b>
          </div>
          <div id='chat_box' ref={chatBoxRef}>
            {messages &&
              messages
                .map((message, index) => (
                  <BubbleChat
                    key={index}
                    username={currentUser.username}
                    sender={message.sender}
                    content={message.content}
                    created={message.created}
                  />
                ))}
          </div>
          <SendBox sendMessage={handleSendMessage} />
        </div>
      </main>
    </>
  )
}

export default ChatPage
