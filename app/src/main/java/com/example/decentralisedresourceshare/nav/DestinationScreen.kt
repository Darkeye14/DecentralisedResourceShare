package com.example.decentralisedresourceshare.nav

sealed class DestinationScreen(val route : String) {
    data object HomeScreen:DestinationScreen("homeScreen")
    data object SignUpScreen:DestinationScreen("SignUpScreen")
    data object PendingScreen:DestinationScreen("pendingScreen")
    data object LoginScreen:DestinationScreen("loginScreen")
    data object NodeSetupScreen:DestinationScreen("NodeSetupScreen")
    data object GeminiSearchScreen:DestinationScreen("geminiSearchScreen")
    data object ApprovedNodeScreen:DestinationScreen("ApprovedNodeScreen")
    data object PicsUploadScreen:DestinationScreen("picsUploadScreen")
    data object PicsDownloadScreen:DestinationScreen("picsDownloadScreen")
    data object DevDetailsScreen:DestinationScreen("devDetailsScreen")
    data object DocsScreen:DestinationScreen("DocsScreen")
}