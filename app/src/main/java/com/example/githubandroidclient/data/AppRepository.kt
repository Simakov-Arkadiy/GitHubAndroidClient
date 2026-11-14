package com.example.githubandroidclient.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.headers

class AppRepository(private val client: HttpClient) {
    suspend fun getRepositories(): List<Repo> {
        // TODO:
    }

    suspend fun getRepository(repoId: String): RepoDetails {
        // TODO:
    }

    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String): String {
        // TODO:
    }

    suspend fun signIn(token: String): UserInfo {
        return client.get("https://api.github.com/user") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                append("X-GitHub-Api-Version", "2026-03-10")
                append(HttpHeaders.Accept, "application/vnd.github+json")
            }
        }.body()
    }
}