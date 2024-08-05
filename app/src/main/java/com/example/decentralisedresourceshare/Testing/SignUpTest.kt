//package com.example.decentralisedresourceshare.Testing
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import com.example.decentralisedresourceshare.nav.DestinationScreen
//import org.junit.Rule
//import org.junit.Test
//
//
//class SignUpScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun signUpScreen_displaysUiElements() {
//        composeTestRule.setContent {
//            DestinationScreen.SignUpScreen
//        }
//
//        composeTestRule.onNodeWithText("SignUp").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
//        composeTestRule.onNodeWithText("SignUp!").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Already a user ? Login! ").assertIsDisplayed()
//    }
//}