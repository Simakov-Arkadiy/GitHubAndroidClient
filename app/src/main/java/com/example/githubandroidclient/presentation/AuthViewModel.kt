package com.example.githubandroidclient.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubandroidclient.R
import com.example.githubandroidclient.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(val repository: AppRepository) : ViewModel() {
    private val _screenState = MutableStateFlow<AuthScreenState>(AuthScreenState.Idle)
    val screenState: StateFlow<AuthScreenState> = _screenState

    private val _valueForTokenTextField =
        MutableStateFlow<TextFieldValue>(TextFieldValue(text = ""))
    val valueForTokenTextField: StateFlow<TextFieldValue> = _valueForTokenTextField

    private val _actions = MutableSharedFlow<AuthScreenAction>(extraBufferCapacity = 1)
    val actions: SharedFlow<AuthScreenAction> = _actions

    init {
        viewModelScope.launch {
            _valueForTokenTextField.value = TextFieldValue(repository.getToken().first())
        }
    }

    fun onSignButtonPressed() {
        if (_valueForTokenTextField.value.text.isBlank()) {
            _screenState.value = AuthScreenState.InvalidInput(R.string.emptyField)
            return
        }
        viewModelScope.launch {
            _screenState.value = AuthScreenState.Loading
            val result = repository.signIn(_valueForTokenTextField.value.text)
            if (result.isSuccess) {
                _screenState.value = AuthScreenState.Idle
                _actions.emit(AuthScreenAction.RouteToMain)
            } else {
                _screenState.value =
                    AuthScreenState.InvalidInput(R.string.failedToken)
            }
        }
    }

    fun onSymbolChanged(newValue: TextFieldValue) {
        _screenState.value = AuthScreenState.Idle
        _valueForTokenTextField.value = newValue
    }

    sealed interface AuthScreenState {
        object Idle : AuthScreenState
        object Loading : AuthScreenState
        data class InvalidInput(val reason: Int) : AuthScreenState
    }

    sealed interface AuthScreenAction {
        object RouteToMain : AuthScreenAction
    }
}