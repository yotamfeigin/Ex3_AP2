const messageService = require('../services/messageService');
const jwt = require('jsonwebtoken');
const { promisify } = require('util');
const chatService = require('../services/chatService');
const tokenService = require('../services/tokenService');

const admin = require('../Tokens/tokenAdmin.js')


exports.addMessage = async (req, res) => {
  try {
    const { msg } = req.body;
    const { chatId } = req.params;
    console.log(req.headers);
    const token = req.headers.authorization.split(' ')[1];
    const parsedToken = JSON.parse(token);
    const tokenValue = parsedToken.token;
    const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
    const sender = decoded.username;
    const message = await messageService.addMessage(chatId, sender, msg);
    if (message) {
      notify(msg, sender, chatId);
      res.status(200).json(message);
    } else {
      res.status(401).json({ error: 'User not found or message creation failed' });
    }
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' });
  }
};
exports.getMessages = async (req, res) => {
    try {
        const { chatId } = req.params
      const token = req.headers.authorization.split(' ')[1];
      const parsedToken = JSON.parse(token);
      const tokenValue = parsedToken.token;
      const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
      const currentUser = decoded.username;
      const messages = await messageService.getMessages(chatId);
      res.send(messages);
    } catch (error) {
      res.status(500).json({ error: 'Internal Server Error' });
    }
  };
  
  exports.getMessagesWithUser = async (req, res) => {
    try {
      const { chatId } = req.params
      const token = req.headers.authorization.split(' ')[1];
      const parsedToken = JSON.parse(token);
      const tokenValue = parsedToken.token;
      const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
      const currentUser = decoded.username;
      const messages = await messageService.getMessagesWithUser(chatId);
      res.send(messages);
    } catch (error) {
      res.status(500).json({ error: 'Internal Server Error' });
    }
  };

  const notify =  async (message, sender, chatId) => {
    const otherUser =  await chatService.getChatUser(chatId, sender);
    const token =  await tokenService.getTokenByUsername(otherUser);
    if(!token) return;
    const payload = {
      notification: {
        title: sender,
        body: message,
      },
      // Optional: Add additional data or customize the notification further
      data: {
        key: 'value',
      },
    };
    console.log(payload);
    admin.messaging().send({
      token: token,
      ...payload,
    })
    .then((response) => {
      console.log('Notification sent successfully:', response);
    })
    .catch((error) => {
      console.error('Error sending notification:', error);
    });
  
  }
  
  

