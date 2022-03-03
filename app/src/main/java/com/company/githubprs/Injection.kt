package com.company.githubprs

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.company.githubprs.api.GithubService
import com.company.githubprs.repo.GithubRepo
import com.company.githubprs.ui.main.MainViewModel

// Ideally DI would be managed using Dagger/Hilt/etc
object Injection {
    private val githubServiceInstance by lazy {
        GithubService.create()
    }

    private val githubRepoInstance: GithubRepo by lazy {
        GithubRepo(githubServiceInstance)
    }

    fun getGithubService(): GithubService {
        return githubServiceInstance
    }

    fun getGithubRepo(): GithubRepo {
        return githubRepoInstance
    }

    fun provideMainViewModelFactory(
        owner: SavedStateRegistryOwner,
        githubRepo: GithubRepo = getGithubRepo(),
    ): ViewModelProvider.Factory {
        return MainViewModel.provideFactory(owner, githubRepo)
    }
}