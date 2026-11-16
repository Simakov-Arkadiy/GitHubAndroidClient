package com.example.githubandroidclient.data

import android.content.Context
import com.example.githubandroidclient.data.KeyValueStorage.Companion.TOKEN_KEY
import com.example.githubandroidclient.dataStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import android.util.Base64
import com.example.githubandroidclient.data.model.ReadmeResponse
import com.example.githubandroidclient.data.model.Repo
import com.example.githubandroidclient.data.model.UserInfo

class AppRepository(
    private val client: HttpClient,
    private val keyValueStorage: KeyValueStorage,
    private val context: Context
) {
    private var repositories = listOf<Repo>()

    suspend fun getRepositories(): Result<List<Repo>> {
        val token = getToken().first()
        val result = client.get("https://api.github.com/user/repos") {
            headers {
                header(HttpHeaders.Authorization, "Bearer $token")
                append("X-GitHub-Api-Version", "2026-03-10")
                append(HttpHeaders.Accept, "application/vnd.github+json")
            }
        }
        return if (result.status.value == 200) {
            repositories = result.body()
            Result.success(result.body())
        } else {
            Result.failure(HttpException("Не удалось получить список репозиториев!"))
        }
    }

    suspend fun getRepository(repoId: Long): Repo {
        return repositories.first { it.id == repoId }
    }

    suspend fun getRepositoryReadme(repoId: Long): Result<String> {
        val repo = getRepository(repoId)
        val token = getToken().first()
        val result =
            client.get("https://api.github.com/repos/${repo.owner.login}/${repo.name}/readme") {
                headers {
                    header(HttpHeaders.Authorization, "Bearer $token")
                    append("X-GitHub-Api-Version", "2026-03-10")
                    append(HttpHeaders.Accept, "application/vnd.github+json")
                }
            }
        val status = result.status.value
        when (status) {
            200 -> {
                val decodedBytes =
                    Base64.decode(result.body<ReadmeResponse>().content, Base64.DEFAULT)
                return Result.success(String(decodedBytes, Charsets.UTF_8))
            }

            404 -> {
                return Result.success("404")
            }

            else -> {
                return Result.failure(HttpException("Не удалось получить readme!"))
            }
        }
    }


    suspend fun signIn(token: String): Result<UserInfo> {
        val result = client.get("https://api.github.com/user") {
            headers {
                header(HttpHeaders.Authorization, "Bearer $token")
                append("X-GitHub-Api-Version", "2026-03-10")
                append(HttpHeaders.Accept, "application/vnd.github+json")
            }
        }
        return if (result.status.value == 200) {
            keyValueStorage.setToken(token)
            Result.success(result.body())
        } else {
            Result.failure(InvalidTokenException("Недействительный токен авторизации!"))
        }
    }

    suspend fun getToken(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN_KEY] ?: ""
            }
    }

    suspend fun clearToken() {
        keyValueStorage.setToken("")
    }
}

class InvalidTokenException(message: String, cause: Throwable? = null) : Exception(message, cause)
class HttpException(message: String, cause: Throwable? = null) : Exception(message, cause)