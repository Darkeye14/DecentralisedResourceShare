package com.example.decentralisedresourceshare.states

import androidx.compose.runtime.mutableStateOf

var inProgress = mutableStateOf( false)
var onError = mutableStateOf( false)
var errorMsg = mutableStateOf( "")
var signIn = mutableStateOf(false)