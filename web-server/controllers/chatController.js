const chatService = require('../services/chatService');
const jwt = require('jsonwebtoken');
const { promisify } = require('util');

exports.createChat = async (req, res) => {
  try {
    const { username } = req.body;
    const token = req.headers.authorization.split(' ')[1];
    const parsedToken = JSON.parse(token);
    const tokenValue = parsedToken.token;

    const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
    const currentUser = decoded.username;
    const chat = await chatService.createChat(currentUser, username);

    if (chat) {
      res.status(200).json(chat);
    } else {
      res.status(401).json({ error: 'User not found or chat creation failed' });
    }
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' });
  }
};

exports.getChats = async (req, res) => {
  try {
    const token = req.headers.authorization.split(' ')[1];
    const parsedToken = JSON.parse(token);
    const tokenValue = parsedToken.token;

    const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
    const currentUser = decoded.username;

    const chats = await chatService.getChats(currentUser)
    res.send(chats);
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' });
  }
};
exports.deleteChat = async (req, res) => {
  try {
    const { id } = req.params
    const token = req.headers.authorization.split(' ')[1];
    const parsedToken = JSON.parse(token);
    const tokenValue = parsedToken.token;
    const decoded = await promisify(jwt.verify)(tokenValue, 'your-secret-key');
    const currentUser = decoded.username;
    const chat = await chatService.deleteChat(id,currentUser);
    if (chat) {
      res.status(200).json(chat);
    } else {
      res.status(401).json({ error: 'Chat not found or chat delete failed' });
    }
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' });
  }
};