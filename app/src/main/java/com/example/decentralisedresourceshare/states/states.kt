package com.example.decentralisedresourceshare.states

import androidx.compose.runtime.mutableStateOf
import com.example.decentralisedresourceshare.data.Node

var inProgress = mutableStateOf( false)
var onError = mutableStateOf( false)
var errorMsg = mutableStateOf( "")
var signIn = mutableStateOf(false)
var savedNodes = mutableStateOf<List<Node>>(listOf())
var approvedSavedNodes = mutableStateOf<List<Node>>(listOf())