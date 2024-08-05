package com.example.decentralisedresourceshare.nav

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.decentralisedresourceshare.screen.ApprovedScreen
import com.example.decentralisedresourceshare.screen.DevDetailsScreen
import com.example.decentralisedresourceshare.screen.DocsScreen
import com.example.decentralisedresourceshare.screen.DownloadImageScreen
import com.example.decentralisedresourceshare.screen.GeminiHomeScreen
import com.example.decentralisedresourceshare.screen.HomeScreen
import com.example.decentralisedresourceshare.screen.NodeSetupScreen
import com.example.decentralisedresourceshare.screen.PendingScreen
import com.example.decentralisedresourceshare.screen.SplashScreen
import com.example.decentralisedresourceshare.screen.UploadImageScreen
import com.example.decentralisedresourceshare.screen.auth.Login
import com.example.decentralisedresourceshare.screen.auth.SignUp
import com.example.decentralisedresourceshare.ui.DcvViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun DIONavigation(
    uriState: MutableStateFlow<String>,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>
) {

    val navController = rememberNavController()
    val viewModel = hiltViewModel<DcvViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreen.route
    ) {
        composable(DestinationScreen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(DestinationScreen.SignUpScreen.route) {
            SignUp(viewModel, navController = navController)
        }
        composable(DestinationScreen.LoginScreen.route) {
            Login(viewModel, navController = navController)
        }
        composable(DestinationScreen.GeminiSearchScreen.route) {
            GeminiHomeScreen(uriState = uriState, imagePicker = imagePicker,viewModel)
        }
        composable(DestinationScreen.NodeSetupScreen.route) {
            NodeSetupScreen(uriState = uriState, imagePicker = imagePicker, viewModel =viewModel , navController =navController )
        }
        composable(DestinationScreen.PendingScreen.route) {
            PendingScreen(viewModel = viewModel, navController =navController )
        }
        composable(DestinationScreen.ApprovedNodeScreen.route) {
            ApprovedScreen(viewModel = viewModel, navController =navController )
        }
        composable(DestinationScreen.PicsUploadScreen.route) {
            UploadImageScreen(viewModel = viewModel, navController = navController)
        }
        composable(DestinationScreen.PicsDownloadScreen.route) {
            DownloadImageScreen(viewModel = viewModel, navController = navController)
        }
        composable(DestinationScreen.DevDetailsScreen.route) {
            DevDetailsScreen(viewModel = viewModel, navController = navController)
        }
        composable(DestinationScreen.DocsScreen.route) {
            DocsScreen(viewModel = viewModel, navController = navController)
        }
        composable(DestinationScreen.SplashScreen.route) {
            SplashScreen(navController = navController, viewModel = viewModel)
        }
    }
}