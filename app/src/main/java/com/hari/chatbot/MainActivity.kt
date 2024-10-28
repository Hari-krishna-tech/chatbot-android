package com.hari.chatbot

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hari.chatbot.screen.ChatRoomListScreen
import com.hari.chatbot.screen.ChatScreen
import com.hari.chatbot.screen.Screen
import com.hari.chatbot.screen.SignInScreen
import com.hari.chatbot.screen.SignUpScreen
import com.hari.chatbot.ui.theme.ChatbotTheme
import com.hari.chatbot.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            ChatbotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationGraph(
                        authViewModel,
                        navController = navController,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.SignUpScreen.route
    ) {

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(authViewModel) {
                navController.navigate(Screen.SignInScreen.route)
            }
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(authViewModel,  {navController.navigate(Screen.SignUpScreen.route)}) {
                navController.navigate(Screen.ChatRoomsScreen.route)
            }
        }


        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomListScreen() {
                navController.navigate("${Screen.ChatScreen.route}/${it.id}")
            }
        }

        composable(Screen.ChatScreen.route + "/{roomId}") {
            val roomId: String = it.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId)
        }
    }
}

