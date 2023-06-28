const { get } = require('http')
const Token = require('../models/fireBaseToken')

exports.createToken = async tokenData => {
  try {
    const { username, token } = tokenData
    let user = await Token.findOne({ username }).exec();
    if(user) {
        user.token = token
        await user.save()
    } else {
        const newToken = new Token({
            username,
            token
        })
        await newToken.save()

    }
   
  } catch (error) {
    throw error
  }
}

exports.getTokenByUsername = async username => {
  try {
    return getTokenByUsername(username)
  } catch (error) {
    throw error
  }
}

const getTokenByUsername = async username => {
    try {
        // Assuming you have a User model defined with the required schema
        const token = await Token.findOne({ username:username }).exec();
        if(!token) {  
            return null
        }
        return token.token
      } catch (error) {
        throw error
      }
    }

exports.deleteToken = async username => {
  try {
    return deleteToken(username)
  } catch (error) {
    throw error
  }
}

const deleteToken = async username => {
  try {
    const deletedToken = await Token.findOneAndDelete({ username });
    if (deletedToken) {
      console.log(`Token with username '${username}' deleted successfully.`);
    } else {
      console.log(`Token with username '${username}' not found.`);
    }
  } catch (error) {
    console.error('Error deleting token:', error);
  }
}