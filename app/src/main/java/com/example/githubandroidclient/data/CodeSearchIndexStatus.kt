package com.example.githubandroidclient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeSearchIndexStatus(
    @SerialName("lexical_search_ok") val lexicalSearchOk: Boolean? = null,
    @SerialName("lexical_commit_sha") val lexicalCommitSha: String? = null,
)