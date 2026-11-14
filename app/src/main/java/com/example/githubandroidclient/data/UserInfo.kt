package com.example.githubandroidclient.data

import kotlinx.serialization.SerialName

data class UserInfo(
    val login: String,
    val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val url: String,
    @SerialName("html_url") val htmlUrl: String,
    val type: String,
    @SerialName("site_admin") val siteAdmin: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    @SerialName("twitter_username") val twitterUsername: String? = null,
    @SerialName("public_repos") val publicRepos: Int = 0,
    @SerialName("public_gists") val publicGists: Int = 0,
    val followers: Int = 0,
    val following: Int = 0,
    @SerialName("private_gists") val privateGists: Int? = null,
    @SerialName("total_private_repos") val totalPrivateRepos: Int? = null,
    @SerialName("owned_private_repos") val ownedPrivateRepos: Int? = null,
    @SerialName("disk_usage") val diskUsage: Int? = null,
    @SerialName("two_factor_authentication") val twoFactorAuthentication: Boolean? = null
)