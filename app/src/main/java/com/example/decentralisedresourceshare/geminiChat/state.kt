package com.example.decentralisedresourceshare.geminiChat

import android.graphics.Bitmap
import com.example.decentralisedresourceshare.data.Chat

data class ChatState (
    val chatList : MutableList<Chat> = mutableListOf(),
    val prompt : String = "",
    val bitmap: Bitmap? = null
)