const messageService = require('../services/messageService');
const jwt = require('jsonwebtoken');
const { promisify } = require('util');

exports.addMessage = async (req, res) => {
  try {
    const { msg } = req.body;
    const { chatId } = req.params;
    const token = req.headers.authorization.split(' ')[1];
    const parsedToken = JSON.parse(token);
    const tokenValue = parsedToken.token;
    const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
    const sender = decoded.username;
    const message = await messageService.addMessage(chatId, sender, msg);
    if (message) {
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