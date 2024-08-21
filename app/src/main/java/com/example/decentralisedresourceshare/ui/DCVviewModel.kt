package com.example.decentralisedresourceshare.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.decentralisedresourceshare.ACCOUNTS
import com.example.decentralisedresourceshare.ALLPICS
import com.example.decentralisedresourceshare.NODES
import com.example.decentralisedresourceshare.SAVEDNODES
import com.example.decentralisedresourceshare.data.Account
import com.example.decentralisedresourceshare.data.AllPicsUploadList
import com.example.decentralisedresourceshare.data.Chat
import com.example.decentralisedresourceshare.data.ChatData
import com.example.decentralisedresourceshare.data.ImgData
import com.example.decentralisedresourceshare.data.Node
import com.example.decentralisedresourceshare.data.PicUid
import com.example.decentralisedresourceshare.geminiChat.ChatState
import com.example.decentralisedresourceshare.geminiChat.ChatUiEvent
import com.example.decentralisedresourceshare.nav.DestinationScreen
import com.example.decentralisedresourceshare.states.allImageUriList
import com.example.decentralisedresourceshare.states.approvedSavedNodes
import com.example.decentralisedresourceshare.states.errorMsg
import com.example.decentralisedresourceshare.states.imageUriList
import com.example.decentralisedresourceshare.states.inProgress
import com.example.decentralisedresourceshare.states.onError
import com.example.decentralisedresourceshare.states.savedNodes
import com.example.decentralisedresourceshare.states.signIn
import com.example.decentralisedresourceshare.util.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DcvViewModel @Inject constructor(
    val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {
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

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            inProgress.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    handleException(it)
                }
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        createAccount(name, email, password)
                        inProgress.value = false
                        navigateTo(navController, DestinationScreen.HomeScreen.route)
                    } else {
                        handleException(customMessage = " SignUp error")
                    }
                }
        }
    }
    init {
        populatePendingNode()
        populateApprovedNode()
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
            } catch (e: Exception) {
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

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)

                    if (event.bitmap != null) {
                        getResponseImage(event.prompt, event.bitmap)

                    } else {
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

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt, bitmap = bitmap, true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }

    private fun getResponse(prompt: String) {
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

    private fun getResponseImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }

    fun setUpNode(
        name: String,
        deviceIfcnNumber: String,
        deviceInfo: String,
        resourceSpecfication: String,
        ramSpecification: String,
        gpuSpecification: String
    ) = CoroutineScope(Dispatchers.Main).launch {
        val auth = auth.currentUser?.uid.toString()
        val node = Node(
            auth = auth,
            name = name,
            deviceIfcnNumber = deviceIfcnNumber,
            deviceInfo = deviceInfo,
            resourceSpecfication = resourceSpecfication,
            ramSpecification = ramSpecification,
            gpuSpecification = gpuSpecification
        )
        db.collection(NODES)
            .document(auth)
            .set(node)

    }

    fun populatePendingNode() = CoroutineScope(Dispatchers.Main).launch {
        db.collection(NODES)
            .whereEqualTo("auth", auth.currentUser?.uid?.toString())
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    savedNodes.value = value.documents.mapNotNull {
                        it.toObject<Node>()
                    }

                }
            }

    }
    fun populateApprovedNode() = CoroutineScope(Dispatchers.Main).launch {
        db.collection(SAVEDNODES)
            .whereEqualTo("auth", auth.currentUser?.uid?.toString())
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "cannot retrieve user")
                }
                if (value != null) {
                    approvedSavedNodes.value = value.documents.mapNotNull {
                        it.toObject<Node>()
                    }

                }
            }

    }
    fun uploadAllImage(
        imgList: List<Uri?>
    ) = CoroutineScope(Dispatchers.IO).launch {

        val storageRef = storage.reference
        imgList.forEach {
            val imgId = UUID.randomUUID().toString()
            val pfp = AllPicsUploadList(
                uid = imgId
            )
            db.collection(ALLPICS).document(imgId).set(pfp)
            val imageRef = storageRef.child("AllImages/$imgId")
            val uploadTask = it.let { it1 ->
                imageRef
                    .putFile(it1!!)
            }
            uploadTask.addOnSuccessListener {
                it.metadata
                    ?.reference
                    ?.downloadUrl
                inProgress.value = false
            }
                .addOnFailureListener {
                    handleException(it)
                }
        }
    }

    fun downloadAllImages(uid: String?) = CoroutineScope(Dispatchers.IO).launch {
        val imageUri = mutableStateOf<Bitmap?>(null)
        inProgress.value = true

        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val storageRef = FirebaseStorage.getInstance().reference

            val bytes = storageRef.child("AllImages/$uid")
                .getBytes(maxDownloadSize)
                .await()
            imageUri.value = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val data = ImgData(
                uid = uid,
                bitmap = imageUri.value
            )
            allImageUriList.add(data)


        } catch (e: Exception) {
            handleException(e)
        }
        inProgress.value = false
    }
    fun onShowAllPics() = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true

        val snapShot = db.collection(ALLPICS)
            .get()
            .await()

        for (doc in snapShot.documents) {
            val post = doc.toObject<PicUid>()
            downloadAllImages(post!!.uid)

        }
        inProgress.value = false
    }

}