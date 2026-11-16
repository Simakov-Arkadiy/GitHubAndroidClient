package com.example.githubandroidclient.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadmeResponse(
    val encoding: String = "base64",
    val content: String = "",
)