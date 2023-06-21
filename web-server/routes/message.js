const express = require('express')
const router = express.Router()
const messageController = require('../controllers/messageController')

router.post('/Chats/:chatId/Messages', messageController.addMessage)
router.get('/Chats/:chatId/Messages', messageController.getMessages)
router.get('/Chats/:chatId', messageController.getMessagesWithUser)
module.exports = router