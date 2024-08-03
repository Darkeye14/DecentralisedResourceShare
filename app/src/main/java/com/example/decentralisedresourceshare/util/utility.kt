package com.example.decentralisedresourceshare.util

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.states.errorMsg
import com.example.decentralisedresourceshare.states.signIn
import com.example.decentralisedresourceshare.ui.DcvViewModel

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.error)
    }
}

fun navigateTo(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}


@Composable
fun OnErrorMessage(
    modifier: Modifier = Modifier
) {
    Toast.makeText(LocalContext.current, errorMsg.value, Toast.LENGTH_SHORT).show()
}

@Composable
fun ImageList() : List<Uri?> {

    var uriState by remember { mutableStateOf<List<Uri?>>(listOf()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        uriState = it
    }

    Button(
        onClick = { launcher.launch("image/jpeg") },
    ) {
        Text(text = "Choose Photos", color = Color.White, style = MaterialTheme.typography.bodySmall)
    }
    Spacer(modifier = Modifier.padding(8.dp))
    return uriState
}

@Composable
fun CheckSignedIn(
    viewModel: DcvViewModel,
    navController: NavController
) {
    val alreadySignedIn = remember { mutableStateOf(false) }
    val signIn = signIn.value
    if (signIn && !alreadySignedIn.value) {
        alreadySignedIn.value = true
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.HomeScreen.route) {
                popUpTo(0)
            }
        }

    } else if (!signIn) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinationScreen.SignUpScreen.route) {
                popUpTo(0)
            }
        }
    }
}
