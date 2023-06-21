const { Chat, ChatUser } = require('../models/chat');
const User = require('../models/user');
const Message = require('../models/message');

exports.createChat = async (username, otherUsername) => {
  try {
    const existingUser = await User.findOne({ username });
    const otherUser = await User.findOne({ username: otherUsername });

    if (!existingUser || !otherUser) {
      return null; // Either user not found
    }

    const existingChat = await Chat.findOne({}).sort({ id: -1 });

    const newChat = new Chat();

    await newChat.save();

    const chatUser1 = new ChatUser({
      chatId: newChat._id,
      username: existingUser.username,
      chatWith: otherUser.username,
    });

    const chatUser2 = new ChatUser({
      chatId: newChat._id,
      username: otherUser.username,
      chatWith: existingUser.username,
    });

    await chatUser1.save();
    await chatUser2.save();

    return newChat;
  } catch (error) {
    console.error('Error creating chat:', error);
    throw error;
  }
};

exports.getChats = async (currentUser) => {
  try {
    const chatUsers = await ChatUser.find({ username: currentUser });
    const chats = [];
    for (const chatUser of chatUsers) {

      const chat = {
        id: chatUser.chatId,
        user: {},
        lastMessage: {},
      };
      const message = await Message.findOne({ chatId:chatUser.chatId })
      .sort({ messageId: -1 })
      .limit(1);
      if(!message){
        chat.lastMessage=null;
      } else {
      chat.lastMessage = {
        id:message.messageId,
        created:message.created,
        content:message.content,
      }
    }
      const otherChatUser = await User.findOne({ username: chatUser.chatWith });
      chat.user = {
        username: otherChatUser.username,
        displayName: otherChatUser.displayName,
        profilePic: otherChatUser.profilePic,
      };
      chats.push(chat);
    }
    return chats;
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
};
exports.deleteChat = async (id,currentUser) => {
  try {
    const existingUser = await User.findOne({ username:currentUser });
    if(!existingUser){
      return 0;
    }
    const chat = await Chat.deleteOne({ _id:id });

    if(chat.deletedCount==0){
      return 0;
    }
    const result = await Message.deleteMany({ chatId: id });
    const result2 = await ChatUser.deleteMany({ chatId: id });
    return chat;
  } catch (error) {
    console.error('Error creating chat:', error);
    throw error;
  }
};