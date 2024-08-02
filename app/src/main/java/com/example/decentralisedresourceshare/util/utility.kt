package com.example.decentralisedresourceshare.util

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.decentralisedresourceshare.states.errorMsg

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCard(
    text: String,
    onClick: () -> Unit,
) {

    Card(modifier = Modifier
        .background(Color.Transparent)
        .size(250.dp)
        .alpha(0.40f)
        .clickable {
            onClick.invoke()
        }
        .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {

        Row(
            modifier = Modifier.fillMaxSize()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = text,
                maxLines = 2,
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
            )
        }

    }
}