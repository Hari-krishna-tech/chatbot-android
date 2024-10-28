package com.hari.chatbot.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signUp(email: String, password: String, firstName: String, lastName: String):Result<Boolean>? {
       try {
          auth.createUserWithEmailAndPassword(email, password).await()
           // add user to firestore
           val user = User(firstName, lastName, email)
           saveUserToFirestore(user)
           saveUserToFirestore(user)
           Log.d("UserRepository", "User signed up successfully")
           return Result.Success(true)
       } catch(e: Exception) {
           return Result.Error(e)
           Log.d("UserRepository", "User signup failed")
       }
    }

    suspend fun signIn(email: String, password: String): Result<Boolean>? {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            return Result.Success(true)
        } catch(e: Exception) {
           return Result.Error(e)
        }
    }

    private suspend fun saveUserToFirestore(user: User) {

        firestore.collection("users").document(user.email)
            .set(user).await()

    }

    suspend fun getCurrentUser(): Result<User> = try {
        val uid = auth.currentUser?.email
        if(uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if(user!= null) {
                Log.d("user2", "$uid")
               Result.Success(user)
            } else {
               Result.Error(Exception("User not found"))
            }
        } else {
            Result.Error(Exception("User not authentication"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
