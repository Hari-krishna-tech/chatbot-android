package com.hari.chatbot.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hari.chatbot.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)

        )

        OutlinedTextField(
            value = password,
            onValueChange = {password= it},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)

        )

        OutlinedTextField(
            value = firstName,
            onValueChange = {firstName= it},
            label = { Text("Firstname") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)

        )

        OutlinedTextField(
            value = lastName,
            onValueChange = {lastName= it},
            label = { Text("Lastname") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)

        )

        Button(
            onClick = {
                authViewModel.signUp(email, password, firstName, lastName)

                email = ""
                password = ""

                firstName= ""
                lastName= ""
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Use primary color from MaterialTheme
                contentColor = MaterialTheme.colorScheme.onPrimary // Use onPrimary color for contrast
            )
        ) {

            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Already Have An Account? Sign In.",
            modifier = Modifier.clickable {
                // add navigation
                onNavigateToSignUp()
            }, style = TextStyle(color = Color.Blue)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
//    SignUpScreen()
}