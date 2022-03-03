package com.company.githubprs.repo

import com.company.githubprs.api.GithubService
import com.company.githubprs.api.models.GetPullRequestsResponse
import com.company.githubprs.models.GithubPullRequest
import com.company.githubprs.models.PullRequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private fun List<GetPullRequestsResponse>.toGithubPullRequests() = this.map { it.toGithubPullRequest() }

private fun GetPullRequestsResponse.toGithubPullRequest(): GithubPullRequest {
    return GithubPullRequest(
        id = this.id,
        url = this.url,
        state = PullRequestState.fromValue(this.state) ?: PullRequestState.OPEN,    // defaults to OPEN
        title = this.title,
        createdAt = this.createdAt,
        closedAt = this.closedAt,
        createdByUserName = this.createdByUser.userName,
        createdByUserAvatarUrl = this.createdByUser.avatarUrl
    )
}

class GithubRepo(
    private val githubService: GithubService
) {

    /**
     * Gets all the pull requests
     * @param owner The user name of the owner of the repo
     * @param repo The name of the Github repository
     * @param state The required state of the pull requests
     * @return the pull requests
     */
    suspend fun getPullRequests(
        owner: String,
        repo: String,
        state: PullRequestState = PullRequestState.CLOSED,
    ): List<GithubPullRequest> = withContext(Dispatchers.IO) {
        return@withContext githubService
            .getPullRequests(owner.trim(), repo.trim(), state)
            .toGithubPullRequests()
    }
}