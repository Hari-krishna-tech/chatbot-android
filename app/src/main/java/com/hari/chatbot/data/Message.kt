package com.hari.chatbot.data

data class Message(
    val senderFirstName: String = "",
    val senderId: String = "",
    val text: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
    val isSendByCurrentUser: Boolean = false
)
