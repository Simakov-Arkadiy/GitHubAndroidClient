package com.example.githubandroidclient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(

    val id: Long,
    @SerialName("node_id")
    val nodeId: String,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: Owner,
    @SerialName("private")
    val isPrivate: Boolean,
    val fork: Boolean,

    @SerialName("html_url")
    val htmlUrl: String,
    val url: String,
    val description: String? = null,
    val homepage: String? = null,

    val language: String? = null,
    val topics: List<String> = emptyList(),

    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
    val forks: Int,
    @SerialName("open_issues")
    val openIssues: Int,
    val watchers: Int,
    @SerialName("open_issues_count")
    val openIssuesCount: Int,
    val size: Int,
    @SerialName("default_branch")
    val defaultBranch: String,

    @SerialName("has_issues")
    val hasIssues: Boolean,
    @SerialName("has_projects")
    val hasProjects: Boolean,
    @SerialName("has_wiki")
    val hasWiki: Boolean,
    @SerialName("has_pages")
    val hasPages: Boolean,
    @SerialName("has_discussions")
    val hasDiscussions: Boolean = false,
    @SerialName("has_pull_requests")
    val hasPullRequests: Boolean = true,
    @SerialName("is_template")
    val isTemplate: Boolean = false,
    val archived: Boolean,
    val disabled: Boolean,

    val visibility: String = "public",
    @SerialName("pull_request_creation_policy")
    val pullRequestCreationPolicy: String? = null,

    @SerialName("pushed_at")
    val pushedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("starred_at")
    val starredAt: String? = null,

    @SerialName("allow_rebase_merge")
    val allowRebaseMerge: Boolean = true,
    @SerialName("allow_squash_merge")
    val allowSquashMerge: Boolean = true,
    @SerialName("allow_merge_commit")
    val allowMergeCommit: Boolean = true,
    @SerialName("allow_auto_merge")
    val allowAutoMerge: Boolean = false,
    @SerialName("allow_update_branch")
    val allowUpdateBranch: Boolean = false,
    @SerialName("delete_branch_on_merge")
    val deleteBranchOnMerge: Boolean = false,
    @SerialName("web_commit_signoff_required")
    val webCommitSignoffRequired: Boolean = false,
    @SerialName("allow_forking")
    val allowForking: Boolean? = null,

    @SerialName("squash_merge_commit_title")
    val squashMergeCommitTitle: String? = null,
    @SerialName("squash_merge_commit_message")
    val squashMergeCommitMessage: String? = null,
    @SerialName("merge_commit_title")
    val mergeCommitTitle: String? = null,
    @SerialName("merge_commit_message")
    val mergeCommitMessage: String? = null,

    @SerialName("temp_clone_token")
    val tempCloneToken: String? = null,
    @SerialName("anonymous_access_enabled")
    val anonymousAccessEnabled: Boolean? = null,

    val license: License? = null,
    val permissions: Permissions? = null,
    @SerialName("code_search_index_status")
    val codeSearchIndexStatus: CodeSearchIndexStatus? = null,

    )