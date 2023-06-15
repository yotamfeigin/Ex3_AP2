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

io.on('connection', socket => {
  socket.on('newMessage', chatId => {
    // Access the chat ID passed as an argument
    socket.broadcast.emit('newMessage', chatId)

    // Perform necessary actions with the chat ID, such as updating the corresponding chat
  })

  socket.on('newContact', () => {
    socket.broadcast.emit('newContact')
  })
  socket.on('deleteContact', () => {
    socket.broadcast.emit('deleteContact')
  })
})

// Middleware
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }))
app.use(express.json())
app.use(bodyParser.json({ limit: '10mb' }))
// Import routes
const userRoutes = require('./routes/user')
const chatRoutes = require('./routes/chat')
const messageRoutes = require('./routes/message')

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

// Serve static files from the "build" folder
app.use(express.static(path.join(__dirname, 'build')))

// Match all routes and serve the index.html file
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'build', 'index.html'))
})
// Start the server
const PORT = process.env.port
server.listen(PORT, () => {})
