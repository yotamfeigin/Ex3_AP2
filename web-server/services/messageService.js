const  Message = require('../models/message');
const User = require('../models/user');
const { Chat, ChatUser } = require('../models/chat');
exports.addMessage = async (chatId, sender,msg) => {
  try {
    const Sender = await User.findOne({ username: sender })
    if (!Sender) {
      return null; //  user not found
    }
    const count = await Message.countDocuments({ chatId:chatId });
    const currentDate = new Date();
    const newMessage = new Message({
      messageId: count,
      created:currentDate,
      sender_username: Sender.username,
      content: msg,
      chatId:chatId,
    });
    await newMessage.save();
    return newMessage;
}catch (error) {
    throw error;
  }
};
exports.getMessages = async (chatId) => {
  try {
    const Messages = await Message.find({ chatId: chatId });
    const messages= [];
    for (const message of Messages) {
      const msg = {
        id: message.messageId,
        created:message.created,
        sender: {},
       content: message.content,
      };
      msg.sender = {
        username: message.sender_username
      };
      messages.push(msg);
    }
    return messages;
  } catch (error) {
    throw error;
  }
};
exports.getMessagesWithUser = async (chatId) => {
  try {
    const msgWithUser = {
      id:chatId,
      users:[],
      messages:[],
    }
    const Messages = await Message.find({ chatId: chatId });
    const messages= [];
    for (const message of Messages) {
      const msg = {
        id: message.messageId,
        created:message.created,
        sender: {},
       content: message.content,
      };
      const send = await User.findOne({username: message.sender_username });
      msg.sender = {
        username: send.username,
        displayName:send.displayName,
        profilePic: send.profilePic,
      };
      messages.push(msg);
    }
    const ChatUsers = await ChatUser.find({ chatId: chatId });
    const chatUsers= [];
    for (const chatUser of ChatUsers) {
      const user = await User.findOne({username: chatUser.username });
      const userToAdd = {
        username: user.username,
        displayName:user.displayName,
        profilePic: user.profilePic,
      };
     chatUsers.push(userToAdd);
    }
    msgWithUser.messages=messages;
    msgWithUser.users=chatUsers;
    return msgWithUser;
  } catch (error) {
    throw error;
  }
};
