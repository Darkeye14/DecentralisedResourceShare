package com.example.decentralisedresourceshare.screen

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.R
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.ui.DcvViewModel
import com.example.decentralisedresourceshare.util.navigateTo
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeSetupScreen(
    uriState : MutableStateFlow<String>,
    imagePicker : ActivityResultLauncher<PickVisualMediaRequest>,
    viewModel: DcvViewModel,
    navController: NavController
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
                val nameState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val deviceIfcnNumberState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val deviceInfoState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val resourceSpecficationState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val ramSpecficationState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val gpuSpecficationState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val search = remember{ mutableStateOf(false) }
                Text(
                    text = "Setup a Node (apply)",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                OutlinedTextField(
                    value = nameState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(),
                    onValueChange = {
                        nameState.value = it
                    },
                    label = {
                        Text(
                            text = "UserName",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                OutlinedTextField(
                    value = deviceIfcnNumberState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(),
                    onValueChange = {
                        deviceIfcnNumberState.value = it
                    },
                    label = {
                        Text(
                            text = "Ifcn Number",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )

                Button(
                    onClick = {
                        navigateTo(navController, DestinationScreen.GeminiSearchScreen.route)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Know How!")
                }

                OutlinedTextField(
                    value = deviceInfoState.value,
                    onValueChange = {
                        deviceInfoState.value = it
                    },
                    colors = TextFieldDefaults.colors(),
                    placeholder = {
                        Text(
                            text = "include company, model name of the device",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Device Info",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        imeAction = ImeAction.Done
//                    )
                )
                OutlinedTextField(
                    value = ramSpecficationState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(),
                    onValueChange = {
                        ramSpecficationState.value = it
                    },
                    label = {
                        Text(
                            text = "Available RAM size",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                OutlinedTextField(
                    value = gpuSpecficationState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(),
                    onValueChange = {
                        gpuSpecficationState.value = it
                    },
                    label = {
                        Text(
                            text = "",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )

                OutlinedTextField(
                    value = resourceSpecficationState.value,
                    onValueChange = {
                        resourceSpecficationState.value = it
                    },
                    colors = TextFieldDefaults.colors(),
                    placeholder = {
                        Text(
                            text = "include any additional info",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Device Info",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        imeAction = ImeAction.Done
//                    )
                )


                Button(
                    onClick = {
                        viewModel.setUpNode(
                            name = nameState.value.text.trim(),
                            deviceIfcnNumber = deviceIfcnNumberState.value.text.trim(),
                            deviceInfo = deviceInfoState.value.text.trim(),
                            resourceSpecfication = resourceSpecficationState.value.text,
                              ramSpecification = ramSpecficationState.value.text,
                              gpuSpecification = gpuSpecficationState.value.text
                        )
                        search.value =true

                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Apply")
                }
                if (search.value){
                    LauncherDialog(navController =navController)
                }
            }
        }
    }
}



@Composable
fun LauncherDialog(navController: NavController) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = {

                        openDialog.value = false
                        navigateTo(navController,DestinationScreen.HomeScreen.route)
                    }) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Successful", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "Your request has been sent for verification. You will be notified when it is approved or when this feature gets integrated. ",
                    fontWeight = FontWeight.SemiBold
                )
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}