package com.example.decentralisedresourceshare.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.decentralisedresourceshare.screen.HomeScreen
import com.example.decentralisedresourceshare.screen.auth.Login
import com.example.decentralisedresourceshare.screen.auth.SignUp
import com.example.decentralisedresourceshare.ui.DcvViewModel


@Composable
fun DIONavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<DcvViewModel>()
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SignUpScreen.route
    ){
        composable(DestinationScreen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(DestinationScreen.SignUpScreen.route){
            SignUp(viewModel,navController = navController)
        }
        composable(DestinationScreen.LoginScreen.route){
            Login(viewModel,navController = navController)
        }
    }
}