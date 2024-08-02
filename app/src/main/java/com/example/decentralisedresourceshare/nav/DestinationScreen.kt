package com.example.decentralisedresourceshare.nav

sealed class DestinationScreen(val route : String) {
    data object HomeScreen:DestinationScreen("homeScreen")
    data object SignUpScreen:DestinationScreen("SignUpScreen")
    data object LoginScreen:DestinationScreen("loginScreen")
}