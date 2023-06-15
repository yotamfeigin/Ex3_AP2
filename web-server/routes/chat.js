const express = require('express')
const router = express.Router()
const chatController = require('../controllers/chatController')

router.post('/Chats', chatController.createChat)
router.get('/Chats', chatController.getChats)
router.delete('/Chats/:id/', chatController.deleteChat)
module.exports = router