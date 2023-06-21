const User = require('../models/user')

exports.createUser = async userData => {
  try {
    const { username, password, displayName, profilePic } = userData

    // Create a new User document
    const user = new User({
      username,
      password,
      displayName,
      profilePic
    })

    // Save the user document to the database
    await user.save()

    return user
  } catch (error) {
    throw error
  }
}

exports.getUserByUsername = async username => {
  try {
    // Assuming you have a User model defined with the required schema
    const user = await User.findOne({ username })
    return user
  } catch (error) {
    throw error
  }
}
