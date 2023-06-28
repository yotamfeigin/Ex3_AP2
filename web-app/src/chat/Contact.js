import { formatDate, breakLongWord } from '../register/functions'

function Contact ({
  picture,
  name,
  setCurrentChat,
  setCurrentPicture,
  token,
  id,
  setMessages,
  setChatId,
  chatId,
  lastMessage,
  setContacts,
  socket
}) {
  const handleClick = async event => {
    setCurrentChat(name)
    setCurrentPicture(picture)
    await setChatId(id)
    getMessages()
  }

  const getMessages = async event => {
    const res = await fetch(`http://localhost:5000/api/Chats/${id}/Messages`, {
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })

    if (res.ok) {
      const messages = await res.json()
      setMessages(messages)
    } else {
      alert('Unable to fetch messages')
    }
  }

  const deleteContact = async event => {
    const res = await fetch(`http://localhost:5000/api/Chats/${id}`, {
      method: 'delete',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    await getChats()
    setCurrentChat('')
    setCurrentPicture('logo.jpg')
    await setChatId(0)
    await setMessages([])
    socket.emit('deleteContact')
  }

  const getChats = async event => {
    const newChatList = await fetch(`http://localhost:5000/api/Chats`, {
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    const chatJson = await newChatList.json()
    setContacts(chatJson)
  }

  if (!lastMessage) {
    lastMessage = {
      content: 'no messages yet',
      created: ''
    }
  }
  if (lastMessage.content.length > 40) {
    lastMessage.content = lastMessage.content.slice(0, 40) + '...'
  }

  return (
    <div
      className='contact_profile'
      id='contact_profile_main'
      onClick={handleClick}
    >
      <img
        src={picture}
        alt='profile picture'
        className='chat_profile_picture'
      />
      <div className='name_last_seen'>
        <b className='textColor contacts_chat'>{name}</b>

        <p className='last_message'>{lastMessage.content}</p>
        <p className='last_seen'>{formatDate(lastMessage.created)}</p>
      </div>
      <button
        type='button'
        className='btn btn-outline-light btn-sm textColor'
        id='delete_contact'
        onClick={deleteContact}
      >
        delete
      </button>
    </div>
  )
}

export default Contact
