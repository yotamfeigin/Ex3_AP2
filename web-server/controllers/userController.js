const jwt = require('jsonwebtoken')
const userService = require('../services/userService')

exports.createUser = async (req, res) => {
  try {
    const { username, password, displayName, profilePic } = req.body

    const user = await userService.createUser({
      username,
      password,
      displayName,
      profilePic
    })

    res.status(200).send('User saved successfully')
  } catch (error) {
    res.status(500).send('Error saving user')
  }
}

exports.getUser = async (req, res) => {
  try {
    const { username } = req.params
    const token = req.headers.authorization.split(' ')[1] // Extract the token from the "Authorization" header
    const parsedToken = JSON.parse(token) // Parse the token string as JSON
    const tokenValue = parsedToken.token // Access the value of the "token" key

    const user = await userService.getUserByUsername(username)

    if (user) {
      // Verify the token
      jwt.verify(tokenValue, 'your-secret-key', (err, decoded) => {
        if (err) {
          // Token is invalid or expired
          res.status(401).json({ error: 'Invalid token' })
        } else {
          // Token is valid
          if (username === decoded.username) {
            const { username, displayName, profilePic } = user
            res.json({ username, displayName, profilePic })
          } else {
            res.status(401).json({ error: 'Invalid token' })
          }
        }
      })
    } else {
      res.status(404).json({ error: 'User not found' })
    }
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' })
  }
}

exports.authenticateUser = async (req, res) => {
  try {
    const { username, password } = req.body

    // Find the user in the database
    const user = await userService.getUserByUsername(username)

    if (user) {
      // Check if the provided password matches the user's password
      if (password === user.password) {
        // Generate a JWT token
        const token = jwt.sign({ username: user.username }, 'your-secret-key')

        // Return the token as the response
        res.json({ token })
      } else {
        res.status(401).json({ error: 'Invalid password' })
      }
    } else {
      res.status(404).json({ error: 'User not found' })
    }
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' })
  }
}
