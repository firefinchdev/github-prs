package com.company.githubprs.models

data class GithubPullRequest(
    val id: Long,
    val url: String,
    val state: PullRequestState,
    val title: String,
    val createdAt: String,
    val closedAt: String,
    val createdByUserName: String,
    val createdByUserAvatarUrl: String,
)