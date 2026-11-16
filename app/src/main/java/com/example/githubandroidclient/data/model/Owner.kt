package com.example.githubandroidclient.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val login: String,
) {
}