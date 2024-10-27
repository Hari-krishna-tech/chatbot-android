package com.hari.chatbot.screen

sealed class Screen(val route: String) {

    object SignInScreen: Screen("signinscreen")
    object SignUpScreen: Screen("signupscreen")
    object ChatRoomsScreen: Screen("chatroomscreen")
    object ChatScreen:Screen("chatscreen")
}