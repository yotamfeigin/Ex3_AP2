const mongoose = require('mongoose');

const chatUserSchema = new mongoose.Schema({
  chatId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Chat',
    required: true,
  },
  username: {
    type: String,
    required: true,
  },
  chatWith: {
    type: String,
    required: true,
  },
});

const chatSchema = new mongoose.Schema({
});

const ChatUser = mongoose.model('ChatUser', chatUserSchema);
const Chat = mongoose.model('Chat', chatSchema);

module.exports = { ChatUser, Chat };
