package com.company.githubprs.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUser(
    @SerialName("id") val id: Long,
    @SerialName("login") val userName: String,
    @SerialName("avatar_url") val avatarUrl: String,
)