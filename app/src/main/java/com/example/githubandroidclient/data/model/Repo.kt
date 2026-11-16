package com.example.githubandroidclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(

    val id: Long,
    val name: String,
    val owner: Owner,
    val description: String? = null,
    val language: String? = null,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
    val license: License? = null,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
)