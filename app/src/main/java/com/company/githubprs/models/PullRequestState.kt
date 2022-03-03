package com.company.githubprs.models

enum class PullRequestState(private val value: String) {
    OPEN("open"),
    CLOSED("closed");

    override fun toString() = value

    companion object {
        fun fromValue(value: String): PullRequestState? {
            return values().firstOrNull { it.value == value }
        }
    }
}