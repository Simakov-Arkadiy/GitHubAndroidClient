package com.example.githubandroidclient.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.githubandroidclient.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(val repository: AppRepository): ViewModel() {
    val token: MutableStateFlow<String>
    val state: StateFlow<State>
    val actions: Flow<Action>

    private val _valueForAuthCodeTextField = mutableStateOf(TextFieldValue(text = ""))
    val valueForAuthCodeTextField: State<TextFieldValue> = _valueForAuthCodeTextField

    fun onSignButtonPressed() {
        // TODO:
    }

//    sealed interface State {
//        object Idle : State
//        object Loading : State
//        data class InvalidInput(val reason: String) : State
//    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }

    // TODO:
}