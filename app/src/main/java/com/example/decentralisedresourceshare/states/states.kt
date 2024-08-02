package com.example.decentralisedresourceshare.states

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.decentralisedresourceshare.data.ImgData
import com.example.decentralisedresourceshare.data.Node
import com.example.decentralisedresourceshare.data.PicUid

var inProgress = mutableStateOf( false)
var onError = mutableStateOf( false)
var errorMsg = mutableStateOf( "")
var signIn = mutableStateOf(false)
var savedNodes = mutableStateOf<List<Node>>(listOf())
var approvedSavedNodes = mutableStateOf<List<Node>>(listOf())
var imageUriList = mutableStateListOf<ImgData?>(null)
var allImageUriList = mutableStateListOf<ImgData?>(null)
var profilesPics = mutableStateOf<List<PicUid>>(listOf())
