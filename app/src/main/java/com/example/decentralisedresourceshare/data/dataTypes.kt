package com.example.decentralisedresourceshare.data


data class HomeCardData(
    val text : String,
    val onClick : ()->Unit
)

data class Account(
    val name : String,
    val email : String,
    val password : String,
    val authId : String
)

 data class Node(
     val auth : String ="",
     val name: String ="",
     val deviceIfcnNumber: String="",
     val deviceInfo: String="",
     val resourceSpecfication: String="",
     val ramSpecification: String="",
     val gpuSpecification: String=""
 )