package com.example.decentralisedresourceshare.dependencyInj

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun providesFirebaseAuthentication(): FirebaseAuth = Firebase.auth


    @Provides
    @Singleton
    fun providesFirebaseFireStoreDataBase(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesStorage(): FirebaseStorage = Firebase.storage
}