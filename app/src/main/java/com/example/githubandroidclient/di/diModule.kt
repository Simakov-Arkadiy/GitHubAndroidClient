package com.example.githubandroidclient.di

import android.R.attr.level
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubandroidclient.data.AppRepository
import com.example.githubandroidclient.data.KeyValueStorage
import com.example.githubandroidclient.presentation.AuthViewModel
import com.example.githubandroidclient.presentation.RepositoriesListScreen
import com.example.githubandroidclient.presentation.RepositoriesListViewModel
import com.example.githubandroidclient.presentation.RepositoryInfoViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.logger.Logger
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val diModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    singleOf(::AppRepository)
    viewModelOf(::AuthViewModel)
    viewModelOf(::RepositoriesListViewModel)
    viewModelOf(::RepositoryInfoViewModel)
    singleOf(::KeyValueStorage)
}