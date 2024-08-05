//package com.example.decentralisedresourceshare.Testing
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import com.example.decentralisedresourceshare.nav.DestinationScreen
//import org.junit.Rule
//import org.junit.Test
//
//@get: Rule
//val composeTestRule = createComposeRule()
//
//class LoginScreenTest {
//    @Test
//    fun testLoginScreen() {
//        composeTestRule.setContent {
//            DestinationScreen.LoginScreen
//        }
//        composeTestRule.onNodeWithText("Log In").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
//        composeTestRule.onNodeWithText("New user ? SignUp! ").assertIsDisplayed()
//    }
//}