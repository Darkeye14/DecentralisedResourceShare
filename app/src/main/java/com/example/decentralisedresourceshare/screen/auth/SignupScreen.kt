
package com.example.decentralisedresourceshare.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.decentralisedresourceshare.states.inProgress
import com.example.decentralisedresourceshare.states.onError
import com.example.decentralisedresourceshare.ui.DcvViewModel
import com.example.decentralisedresourceshare.util.CommonProgressBar
import com.example.decentralisedresourceshare.util.OnErrorMessage
import com.example.decentralisedresourceshare.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
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


                val emailState = remember {
                    mutableStateOf(TextFieldValue())
                }

                val passwordState = remember {
                    mutableStateOf(TextFieldValue())
                }
                val nameState = remember {
                    mutableStateOf(TextFieldValue())
                }

//                Image(
//                    painter = painterResource(id = R.drawable.education),
//                    contentDescription = "LogIn",
//                    modifier = Modifier
//                        .width(200.dp)
//                        .padding(top = 16.dp)
//                        .padding(8.dp)
//                )

                Text(
                    text = "SignUp",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                if (inProgress.value) {
                    CommonProgressBar()
                }

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
                            text = "Name",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )

                OutlinedTextField(
                    value = emailState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(),
                    onValueChange = {
                        emailState.value = it
                    },
                    label = {
                        Text(
                            text = "Email",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                OutlinedTextField(
                    colors = TextFieldDefaults.colors(),
                    value = passwordState.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        passwordState.value = it
                    },
                    label = {
                        Text(
                            text = "Password",
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                )
                if (onError.value){
                    OnErrorMessage()
                }

                Button(
                    onClick = {
                        viewModel.signUp1(
                            name = nameState.value.text.trim(),
                            emailState.value.text.trim(),
                            passwordState.value.text.trim(),
                            navController
                        )

                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "SignUp!")
                }
                Text(
                    text = "Already a user ? Login! ",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navigateTo(navController, DestinationScreen.LoginScreen.route)
                        }
                )

            }

        }
    }
}