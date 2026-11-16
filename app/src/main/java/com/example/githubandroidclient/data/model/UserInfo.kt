package com.example.githubandroidclient.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val login: String,
)