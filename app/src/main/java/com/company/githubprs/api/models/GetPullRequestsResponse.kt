package com.company.githubprs.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPullRequestsResponse(
    @SerialName("id") val id: Long,
    @SerialName("url") val url: String,
    @SerialName("state") val state: String,
    @SerialName("title") val title: String,
    @SerialName("user") val createdByUser: GithubUser,
    @SerialName("created_at") val createdAt: String,
    @SerialName("closed_at") val closedAt: String,
)

