package com.example.websocketdemo.controller

import com.example.websocketdemo.model.ChatMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener {
    private val logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)

    @Autowired
    private lateinit var messagingTemplate: SimpMessageSendingOperations

    @EventListener
    fun handleWebSocketConnectionListener(event: SessionConnectEvent){
        logger.info("Received a new web socket connection")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent){
        val headerAccessor = StompHeaderAccessor.wrap(event.message)

        val username = headerAccessor.sessionAttributes?.get("username")
        if(username is String){
            logger.info("User Disconnected: $username")

            val chatMessage = ChatMessage()
            chatMessage.apply {
                type = ChatMessage.MessageType.LEAVE
                sender = username
            }

            messagingTemplate.convertAndSend("/topic/public", chatMessage)
        }


    }

}