//package com.example.decentralisedresourceshare.Testing
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import com.example.decentralisedresourceshare.nav.DestinationScreen
//import org.junit.Rule
//import org.junit.Test
//
//class NodeSetupScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun nodeSetupScreen_displaysUiElements() {
//        composeTestRule.setContent {
//            DestinationScreen.NodeSetupScreen
//        }
//
//        composeTestRule.onNodeWithText("Setup a Node (apply)").assertIsDisplayed()
//        composeTestRule.onNodeWithText("UserName").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Ifcn Number").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Know How!").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Device Info").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Available RAM size").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Available Gpu Resource size ").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Apply").assertIsDisplayed()
//    }
//}