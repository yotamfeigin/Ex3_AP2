require('dotenv').config({ path: './config/.env.local' })
const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const path = require('path')
const cors = require('cors')
const customEnv = require('custom-env')
const mongoose = require('mongoose')
app.use(cors())

const http = require('http')
const socketio = require('socket.io')
const server = http.createServer(app)
const io = new socketio.Server(server, { cors: { origin: '*' } })
const admin = require('./Tokens/tokenAdmin.js')

io.on('connection', socket => {

  socket.on('newMessage', (chatId) => {
    // Access the chat ID passed as an argument
    socket.broadcast.emit('newMessage', chatId)

    // Perform necessary actions with the chat ID, such as updating the corresponding chat
  })

  socket.on('newContact', (username) => {
    socket.broadcast.emit('newContact', username)
  })
  socket.on('deleteContact', () => {
    socket.broadcast.emit('deleteContact')
  })
})

// Import routes
const userRoutes = require('./routes/user')
const chatRoutes = require('./routes/chat')
const messageRoutes = require('./routes/message')

// Middleware
app.use(bodyParser.json({ limit: '10mb' }))
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }))
app.use(express.json())
app.use(express.static(path.join(__dirname, 'public')))
customEnv.env(process.env.NODE_ENV, './config')


mongoose.connect(process.env.CONNECTION_STRING, {
  useNewUrlParser: true,
  useUnifiedTopology: true
})

// Routes
app.use('/api', userRoutes)
app.use('/api', chatRoutes)
app.use('/api', messageRoutes)




// Start the server
const PORT = process.env.port
server.listen(PORT, () => {

})
