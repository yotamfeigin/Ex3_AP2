const mongoose = require('mongoose')

const messageSchema = new mongoose.Schema({
  messageId: {
    type: Number,
    required: true
  },
  created: {
    type: Date,
    required: true
  },
    sender_username: {
      type: String,
      required: true
    },
  content: {
    type: String,
    required: true
  },
  chatId: {
    type: String,
    required: true
  }
})

const Message = mongoose.model('Message', messageSchema)

module.exports = Message
