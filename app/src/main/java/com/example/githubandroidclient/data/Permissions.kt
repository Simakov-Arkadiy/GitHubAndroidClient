package com.example.githubandroidclient.data

import kotlinx.serialization.Serializable

@Serializable
data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean,
    val triage: Boolean? = null,
    val maintain: Boolean? = null,
)