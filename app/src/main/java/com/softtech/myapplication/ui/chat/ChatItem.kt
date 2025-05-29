package com.softtech.myapplication.ui.chat

data class ChatItem(val message: String, val type: ChatType)

enum class ChatType {
    AI,
    USER
}