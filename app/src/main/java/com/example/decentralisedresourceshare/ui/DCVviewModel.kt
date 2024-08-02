package com.example.decentralisedresourceshare.ui

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.ACCOUNTS
import com.example.decentralisedresourceshare.data.Account
import com.example.decentralisedresourceshare.data.Chat
import com.example.decentralisedresourceshare.data.ChatData
import com.example.decentralisedresourceshare.geminiChat.ChatState
import com.example.decentralisedresourceshare.geminiChat.ChatUiEvent
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.states.errorMsg
import com.example.decentralisedresourceshare.states.inProgress
import com.example.decentralisedresourceshare.states.onError
import com.example.decentralisedresourceshare.states.signIn
import com.example.decentralisedresourceshare.util.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DcvViewModel @Inject constructor(
    val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
): ViewModel() {
    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
      //      getUserData(it)
        }
    }
    fun signUp1(
        name: String,
        email: String,
        password: String,
        navController: NavController
    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, password)

            .addOnFailureListener {
                handleException(it)
            }
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    createAccount(name,email, password)
                    inProgress.value = false
                    navigateTo(navController, DestinationScreen.HomeScreen.route)
                } else {
                    handleException(customMessage = " SignUp error")
                }
            }
    }

    private fun createAccount(
        name: String,
        email: String,
        password: String,
    ) = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        val acc = auth.currentUser?.uid?.let {
            Account(
                name = name,
                email = email,
                password = password,
                authId = it
            )
        }
        if (acc != null) {
            db.collection(ACCOUNTS)
                .document()
                .set(acc)
        }

    }

    fun login(
        email: String,
        password: String,
        navController: NavController

    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true

        val admin = db.collection(ACCOUNTS)

            .where(
                Filter.and(
                    Filter.equalTo("email", email),
                    Filter.equalTo("password", password),

                    )
            )
            .get()
            .await()
        inProgress.value = false

        if (email.isEmpty() || password.isEmpty()) {
            handleException(customMessage = "Please fill all the fields")

        } else {
            inProgress.value = true
            try {
                if (!admin.isEmpty) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnFailureListener {
                            handleException(it)
                        }
                        .addOnCompleteListener {

                            if (it.isSuccessful) {
                                signIn.value = true
                                inProgress.value = false
                                auth.currentUser?.uid?.let {
                                    //                        getUserData(it)
                                }
                                afterLogin(navController)
                            } else {
                                handleException(it.exception, customMessage = "Login failed")
                            }

                        }
                } else {
                    errorMsg.value = "Invalid User"
                    onError.value = true
                }
            } catch (e: FirebaseAuthException) {
                handleException(e)
            }
            catch (e: Exception){
                handleException(e)
            }
            inProgress.value = false

        }
    }

    fun handleException(
        exception: Exception? = null,
        customMessage: String = ""
    ) {
        onError.value = true
        if (exception != null) {
            errorMsg.value = exception.toString()
        } else {
            errorMsg.value = customMessage
        }

        errorMsg.value = exception.toString()
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage
        val message =
            if (customMessage.isEmpty())
                errorMsg
            else
                customMessage
        onError.value = false
        inProgress.value = false
    }

    private fun afterLogin(
        navController: NavController
    ) = CoroutineScope(Dispatchers.Main).launch {
        delay(500)
        navigateTo(navController, DestinationScreen.HomeScreen.route)
 //       populatePost()
    }


    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event: ChatUiEvent){
        when(event){
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()){
                    addPrompt(event.prompt,event.bitmap)

                    if (event.bitmap !=null){
                        getResponseImage(event.prompt,event.bitmap)

                    }
                    else{
                        getResponse(event.prompt)
                    }
                }
            }
            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }
        }
    }
    private fun addPrompt(prompt : String, bitmap: Bitmap?){
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt, bitmap = bitmap,true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }
    private fun getResponse(prompt : String){
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }

    private fun getResponseImage(prompt : String,bitmap: Bitmap){
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt,bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }


}