package com.example.decentralisedresourceshare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.decentralisedresourceshare.R
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.states.allImageUriList
import com.example.decentralisedresourceshare.states.profilesPics
import com.example.decentralisedresourceshare.ui.DcvViewModel
import com.example.decentralisedresourceshare.util.navigateTo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadImageScreen(
    viewModel: DcvViewModel,
    navController: NavController
) {
    viewModel.populateApprovedNode()
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
        viewModel.onShowAllPics()
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
                .fillMaxHeight()
                .fillMaxSize(),
        ) {
            profilesPics.value.forEach {
                Text(text = it.uid.toString())
            }
            Image(
                alpha = 0.40f,
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            if (allImageUriList.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(color = Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error)
                    ) {

                        Text(
                            text = "No Photos Available",
                            fontSize = 25.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                ) {
                    items(allImageUriList) {
                        if (it != null) {
                            AsyncImage(
                                model = it.bitmap,
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable {
//                                        navigateTo(
//                                            navController = navController,
//                                            DestinationScreen.DeletePicsScreen.createRoute(uid = it.uid!!,type = "allPics")
//                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
