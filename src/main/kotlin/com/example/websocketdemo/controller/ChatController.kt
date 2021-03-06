package com.example.websocketdemo.controller

import com.example.websocketdemo.model.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import java.lang.RuntimeException

@Controller
class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload chatMessage: ChatMessage): ChatMessage{
        return chatMessage
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(@Payload chatMessage: ChatMessage,
        headerAccessor: SimpMessageHeaderAccessor): ChatMessage{
        // Add username in web socket session
        headerAccessor.sessionAttributes?.put("username", chatMessage.sender)
        return chatMessage
    }
}