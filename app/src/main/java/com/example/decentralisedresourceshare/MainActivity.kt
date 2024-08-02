package com.example.decentralisedresourceshare

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.decentralisedresourceshare.nav.DIONavigation
import com.example.decentralisedresourceshare.screen.GeminiHomeScreen
import com.example.decentralisedresourceshare.ui.theme.DecentralisedResourceShareTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val uriState = MutableStateFlow("")
    private val imagePicker =
        registerForActivityResult<PickVisualMediaRequest, Uri?>(
            ActivityResultContracts.PickVisualMedia()
        ){uri ->
            uriState.update { uri.toString() }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DecentralisedResourceShareTheme {
               DIONavigation(uriState = uriState, imagePicker = imagePicker)

            }
        }
    }
}

@Composable
fun CrashlyticsTest(){
    Button(onClick = { throw RuntimeException("Test Crash")}) {
        Text(text = "Test Crash")
    }
}
