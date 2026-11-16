package com.example.githubandroidclient.presentation

import com.example.githubandroidclient.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubandroidclient.data.AppRepository
import com.example.githubandroidclient.data.model.Repo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoriesListViewModel(val repository: AppRepository) : ViewModel() {

    private val _screenState =
        MutableStateFlow<RepositoriesListScreenState>(RepositoriesListScreenState.Empty)
    val screenState: StateFlow<RepositoriesListScreenState> = _screenState
    private val _actions =
        MutableSharedFlow<RepositoriesListScreenAction>(extraBufferCapacity = 1)
    val actions: SharedFlow<RepositoriesListScreenAction> = _actions

    init {
        viewModelScope.launch {
            _screenState.value = RepositoriesListScreenState.Loading
            val result = repository.getRepositories()
            if (result.isSuccess) {
                _screenState.value =
                    RepositoriesListScreenState.Loaded(result.getOrNull() ?: emptyList())
            } else {
                _screenState.value =
                    RepositoriesListScreenState.Error(error = R.string.errorMessageForListRepositories)
            }
        }
    }

    fun logOutButtonPressed() {
        viewModelScope.launch {
            repository.clearToken()
            _actions.emit(RepositoriesListScreenAction.RouteToAuthScreen)
        }
    }

    fun clickableRowPressed(repoId: String) {
        viewModelScope.launch {
            _actions.emit(RepositoriesListScreenAction.RouteToDetailInfoScreen(repoId))
        }
    }

    sealed interface RepositoriesListScreenState {
        object Loading : RepositoriesListScreenState
        data class Loaded(val repos: List<Repo>) : RepositoriesListScreenState
        data class Error(val error: Int) : RepositoriesListScreenState
        object Empty : RepositoriesListScreenState
    }

    sealed interface RepositoriesListScreenAction {
        object RouteToAuthScreen : RepositoriesListScreenAction
        data class RouteToDetailInfoScreen(val repoId: String) : RepositoriesListScreenAction
    }
}