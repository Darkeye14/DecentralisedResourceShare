package com.example.decentralisedresourceshare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.R
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,

    ) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
                .fillMaxHeight()
                .fillMaxSize(),
        ) {
            Image(
                alpha = 0.40f,
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = "Our Main Services",
                            maxLines = 2,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    HomeScreenCard(Modifier.weight(1f), text = "Smart Search") {
                        navigateTo(navController, DestinationScreen.GeminiSearchScreen.route)
                    }
                    HomeScreenCard(Modifier.weight(1f), text = "Node Setup") {

                        navigateTo(navController, DestinationScreen.NodeSetupScreen.route)
                    }
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    HomeScreenCard(Modifier.weight(1f), text = "Pending requests") {
                        navigateTo(navController,DestinationScreen.PendingScreen.route)
                    }
                    HomeScreenCard(Modifier.weight(1f), text = "Approved requests") {

                        navigateTo(navController, DestinationScreen.ApprovedNodeScreen.route)
                    }
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    HomeScreenCard(Modifier.weight(1f), text = "Upload Photos") {
                        navigateTo(navController,DestinationScreen.PicsUploadScreen.route)
                    }
                    HomeScreenCard(Modifier.weight(1f), text = "Photo Gallery") {

                        navigateTo(navController, DestinationScreen.PicsDownloadScreen.route)
                    }
                }
                Row(modifier = Modifier.padding(8.dp)) {
                    HomeScreenCard(Modifier.weight(1f), text = "Docs") {
                        navigateTo(navController,DestinationScreen.DocsScreen.route)
                    }
                    HomeScreenCard(Modifier.weight(1f), text = "Developer's Details") {

                        navigateTo(navController, DestinationScreen.DevDetailsScreen.route)
                    }
                }

            }
        }
    }
}

@Composable
fun HomeScreenCard(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Card(modifier = modifier
        .height(250.dp)
        .clickable {
            onClick.invoke()
        }
        .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {
        Row(
            modifier = Modifier.fillMaxSize(),
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