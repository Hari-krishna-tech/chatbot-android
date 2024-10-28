package com.hari.chatbot.screen


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hari.chatbot.data.Result
import com.hari.chatbot.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignUp: () -> Unit,
    onSignInSuccess: () -> Unit
    
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val result by authViewModel.authResult.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        )

        OutlinedTextField(
            value = password,
            onValueChange = {password= it},
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        )


        Button(
            onClick = {
                authViewModel.signIn(email, password)
                when(result) {
                    is Result.Success -> {
                        Log.d("SignInSucess", "Success")
                        onSignInSuccess()
                    }
                    is Result.Error -> {
                        Log.d("SignInFailed", "Failed")
                   }

                    null -> {
                    Log.d("SignInNull", "Null")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Use primary color from MaterialTheme
                contentColor = MaterialTheme.colorScheme.onPrimary // Use onPrimary color for contrast
            )
        ) {

            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Don't Have An Account? Sign Up.",
            modifier = Modifier.clickable {
                // add navigation
                onNavigateToSignUp()
            }, style = TextStyle(color = Color.Blue)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
//    SignInScreen()
}
