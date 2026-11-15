package com.example.githubandroidclient.presentation

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubandroidclient.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(val repository: AppRepository) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val screenState: StateFlow<ScreenState> = _screenState

    private val _valueForTokenTextField = MutableStateFlow<TextFieldValue>(TextFieldValue(text = ""))
    val valueForTokenTextField: StateFlow<TextFieldValue> = _valueForTokenTextField

    private val _actions = MutableSharedFlow<Action>(extraBufferCapacity = 1)
    val actions: SharedFlow<Action> = _actions
    init{
        viewModelScope.launch {
            _valueForTokenTextField.value = TextFieldValue(repository.getToken().first())
        }
    }

    fun onSignButtonPressed() {
        if (_valueForTokenTextField.value.text.isBlank()) {
            _screenState.value = ScreenState.InvalidInput("Токен не может быть пустым")
            return
        }
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            val result = repository.signIn(_valueForTokenTextField.value.text)
            if(result.isSuccess){
                _screenState.value = ScreenState.Idle
                _actions.emit(Action.RouteToMain)
            }
            else{
                _screenState.value =
                    ScreenState.InvalidInput(result.exceptionOrNull()?.message?: "Неизвестная ошибка")
            }
        }
    }

    fun onSymbolChanged(newValue: TextFieldValue) {
        _screenState.value = ScreenState.Idle
        _valueForTokenTextField.value = newValue
    }

    sealed interface ScreenState {
        object Idle : ScreenState
        object Loading : ScreenState
        data class InvalidInput(val reason: String) : ScreenState
    }

    sealed interface Action {
        object RouteToMain : Action
    }
}