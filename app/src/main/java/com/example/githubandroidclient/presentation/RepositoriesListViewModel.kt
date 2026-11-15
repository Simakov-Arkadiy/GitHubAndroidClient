package com.example.githubandroidclient.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubandroidclient.data.AppRepository
import com.example.githubandroidclient.data.Repo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoriesListViewModel(val repository: AppRepository) : ViewModel() {

    private val _screenState =
        MutableStateFlow<RepositoriesListViewModel.ScreenState>(RepositoriesListViewModel.ScreenState.Empty)
    val screenState: StateFlow<RepositoriesListViewModel.ScreenState> = _screenState
    private val _actions =
        MutableSharedFlow<RepositoriesListViewModel.Action>(extraBufferCapacity = 1)
    val actions: SharedFlow<RepositoriesListViewModel.Action> = _actions

    init {
        viewModelScope.launch {
            _screenState.value = RepositoriesListViewModel.ScreenState.Loading
            repository.getRepositories()
            val result = repository.getRepositories()
            if (result.isSuccess) {
                _screenState.value =
                    RepositoriesListViewModel.ScreenState.Loaded(result.getOrNull() ?: emptyList())
            } else {
                _screenState.value = RepositoriesListViewModel.ScreenState.Error(
                    result.exceptionOrNull()?.message ?: "Неизвестная ошибка!"
                )
            }
        }
    }

    fun logOutButtonPressed() {
        viewModelScope.launch {
            repository.clearToken()
            _actions.emit(RepositoriesListViewModel.Action.RouteToAuthScreen)
        }
    }

    fun clickableRowPressed(repoId: String) {
        viewModelScope.launch {
            repository.clearToken()
            _actions.emit(RepositoriesListViewModel.Action.RouteToDetailInfoScreen(repoId))
        }
    }

    sealed interface ScreenState {
        object Loading : ScreenState
        data class Loaded(val repos: List<Repo>) : ScreenState
        data class Error(val error: String) : ScreenState
        object Empty : ScreenState
    }

    sealed interface Action {
        object RouteToAuthScreen : Action
        data class RouteToDetailInfoScreen(val repoId: String) : Action
    }
}