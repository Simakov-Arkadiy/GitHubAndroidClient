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
import org.koin.core.annotation.InjectedParam

class RepositoryInfoViewModel(val repository: AppRepository, @InjectedParam val repoId: Long) :
    ViewModel() {
    private val _screenState =
        MutableStateFlow<DetailInfoScreenState>(DetailInfoScreenState.Loading)
    val screenState: StateFlow<DetailInfoScreenState> = _screenState

    private val _actions =
        MutableSharedFlow<DetailInfoScreenAction>(extraBufferCapacity = 1)
    val actions: SharedFlow<DetailInfoScreenAction> = _actions

    init {
        viewModelScope.launch {
            _screenState.value = DetailInfoScreenState.Loading
            val repo = repository.getRepository(repoId)
            _screenState.value = DetailInfoScreenState.Loaded(
                repo,
                ReadmeState.Loading
            )
            val result = repository.getRepositoryReadme(repoId)
            if (result.isSuccess) {
                if (result.getOrNull() == "404") {
                    _screenState.value = DetailInfoScreenState.Loaded(
                        repo,
                        ReadmeState.Empty
                    )
                } else {
                    _screenState.value = DetailInfoScreenState.Loaded(
                        repo,
                        ReadmeState.Loaded(result.getOrNull() ?: "")
                    )
                }
            } else {
                _screenState.value = DetailInfoScreenState.Loaded(
                    repo,
                    ReadmeState.Error(R.string.errorMessageForDetailInfo)
                )
            }
        }
    }

    fun backButtonPressed() {
        viewModelScope.launch {
            _actions.emit(DetailInfoScreenAction.RouteToRepositoriesListScreen)
        }
    }

    fun logOutButtonPressed() {
        viewModelScope.launch {
            repository.clearToken()
            _actions.emit(DetailInfoScreenAction.RouteToAuthScreen)
        }
    }

    sealed interface DetailInfoScreenState {
        object Loading : DetailInfoScreenState
        data class Loaded(
            val githubRepo: Repo,
            val readmeState: ReadmeState
        ) : DetailInfoScreenState
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: Int) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }

    sealed interface DetailInfoScreenAction {
        object RouteToRepositoriesListScreen : DetailInfoScreenAction
        object RouteToAuthScreen : DetailInfoScreenAction
    }
}