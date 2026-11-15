package com.example.githubandroidclient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val login: String,
    val id: Long,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("html_url") val htmlUrl: String,
    val url: String,
    @SerialName("type") val type: String,
    @SerialName("site_admin") val siteAdmin: Boolean,
    val name: String? = null,
    val email: String? = null,
    @SerialName("gravatar_id") val gravatarId: String? = null,) {
}