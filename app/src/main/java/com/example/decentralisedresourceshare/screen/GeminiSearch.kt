package com.example.decentralisedresourceshare.screen

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.size.Size
import com.example.decentralisedresourceshare.R
import com.example.decentralisedresourceshare.geminiChat.ChatUiEvent
import com.example.decentralisedresourceshare.ui.DcvViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiHomeScreen(
    uriState : MutableStateFlow<String>,
    imagePicker : ActivityResultLauncher<PickVisualMediaRequest>,
    chatViewModel: DcvViewModel
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
        ChatScreen(paddingValues = it,
            uriState = uriState,
            imagePicker = imagePicker,
            chatViewModel = chatViewModel
        )
    }
}

@Composable
fun ChatScreen(paddingValues: PaddingValues,
               uriState :MutableStateFlow<String>,
               imagePicker : ActivityResultLauncher<PickVisualMediaRequest>,
               chatViewModel: DcvViewModel
) {
  //  val chatViewModel = viewModel<DcvViewModel>()
    val chatState = chatViewModel.chatState.collectAsState().value
    val bitmap = getBitmap(uriState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            reverseLayout = true
        ) {
            itemsIndexed(chatState.chatList){index, chat ->
                if(chat.isFromUser){
                    UserChatItem(prompt =chat.prompt ,
                        bitmap = chat.bitmap
                    )
                }else{
                    ModelChatItem(response = chat.prompt)
                }
            }

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                bitmap?.let {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(bottom = 4.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = "image",
                        bitmap = it.asImageBitmap()
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding()
                        .size(40.dp)
                        .clickable {
                            imagePicker.launch(
                                PickVisualMediaRequest
                                    .Builder()
                                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    .build()
                            )
                        },
                    imageVector = Icons.Rounded.AddPhotoAlternate,
                    contentDescription ="add photo",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextField(modifier = Modifier
                .weight(1f),
                value = chatState.prompt,
                onValueChange ={
                    chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it))
                },
                placeholder = {
                    Text(text = "Type a prompt")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .padding()
                    .size(40.dp)
                    .clickable {
                        chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt,bitmap))
                        uriState.update { "" }
                    },
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription ="send prompt",
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }
}

@Composable
fun UserChatItem(
    prompt : String, bitmap: Bitmap?
) {
    Column(
        modifier =Modifier
            .padding(start = 100.dp, bottom = 22.dp)
    ) {
        bitmap?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = "image",
                bitmap = it.asImageBitmap()
            )
        }
        Text(text = prompt,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 17.sp
        )
    }
}

@Composable
fun ModelChatItem(
    response : String,
) {
    Column(
        modifier =Modifier
            .padding(end = 100.dp, bottom = 22.dp)
    ) {

        Text(text = response,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = 17.sp
        )
    }
}
@Composable
private fun getBitmap (
    uriState : MutableStateFlow<String>
) : Bitmap?{
    val uri = uriState.collectAsState().value
    val imageState : AsyncImagePainter.State = rememberAsyncImagePainter(
        model =
            coil.request.ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .build()
    ).state
    if(imageState is AsyncImagePainter.State.Success){
        return imageState.result.drawable.toBitmap()
    }
    return null
}