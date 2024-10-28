package com.hari.chatbot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hari.chatbot.data.Message
import com.hari.chatbot.data.MessageRepository
import com.hari.chatbot.data.Result
import com.hari.chatbot.data.User
import com.hari.chatbot.data.UserRepository
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {

    private val messageRepository: MessageRepository

    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(AuthViewModel.Injection.instance())
        userRepository = UserRepository(FirebaseAuth.getInstance(), AuthViewModel.Injection.instance())

        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>>()
    val message: LiveData<List<Message>> get()= _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    fun loadMessages() {
        viewModelScope.launch {
            if(_roomId != null) {
                messageRepository.getChatMessages(_roomId.value.toString())
                    .collect{_messages.value = it}
            }
        }
    }

    fun sendMessage(text:String) {
        if(_currentUser.value != null) {
            val message = Message(
                senderFirstName =  _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when(messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Result.Success -> Unit
                    is Result.Error -> {

            }
            }
            }
        }
    }

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }


    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> _currentUser.value = result.data
                is Result.Error -> {

                }
            }
        }
    }
}