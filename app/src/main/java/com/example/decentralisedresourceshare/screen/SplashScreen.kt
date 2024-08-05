package com.example.decentralisedresourceshare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.ui.DcvViewModel
import com.example.decentralisedresourceshare.util.CheckSignedIn

@Composable
fun SplashScreen(navController: NavController,
                 viewModel: DcvViewModel
) {
    Box(modifier= Modifier
        .background(MaterialTheme.colorScheme.primary)
        .fillMaxSize(),
    ) {
        CheckSignedIn(viewModel = viewModel,
            navController = navController
        )

    }
}