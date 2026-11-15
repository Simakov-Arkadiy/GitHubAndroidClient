package com.example.githubandroidclient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class License(
    val key: String,
    val name: String,
    @SerialName("spdx_id") val spdxId: String?,
    @SerialName("node_id") val nodeId: String,
    @SerialName("html_url") val htmlUrl: String = "",
    val url: String? = null,
)