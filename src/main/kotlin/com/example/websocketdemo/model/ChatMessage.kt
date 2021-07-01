package com.example.websocketdemo.model

class ChatMessage {
    enum class MessageType{
        CHAT,
        JOIN,
        LEAVE
    }

    lateinit var type: MessageType
    lateinit var content: String
    lateinit var sender: String
}