package com.company.githubprs.api

import com.company.githubprs.api.models.GetPullRequestsResponse
import com.company.githubprs.models.PullRequestState
import com.company.githubprs.utils.json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    /**
     * Gets all the pull requests
     * @param owner The user name of the owner of the repo
     * @param repo The name of the Github repository
     * @param state The required state of the pull requests
     * @return the pull requests
     */
    @GET("https://api.github.com/repos/{owner}/{repo}/pulls")
    suspend fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: PullRequestState = PullRequestState.CLOSED,
    ): List<GetPullRequestsResponse>

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        fun create(
            baseUrl: String = "https://api.github.com/",
            interceptors: List<Interceptor> = listOf(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            ),
            convertorFactory: Converter.Factory = json.asConverterFactory("application/json".toMediaType())
        ): GithubService {
            val client = OkHttpClient.Builder()
                .apply { interceptors.forEach { addInterceptor(it) } }
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(convertorFactory)
                .build()
                .create(GithubService::class.java)
        }
    }
}