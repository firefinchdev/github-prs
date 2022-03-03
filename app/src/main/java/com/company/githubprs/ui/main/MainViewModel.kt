package com.company.githubprs.ui.main

import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.company.githubprs.models.GithubPullRequest
import com.company.githubprs.models.PullRequestState
import com.company.githubprs.repo.GithubRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

private const val KEY_USER_NAME = "KEY_USER_NAME"
private const val KEY_REPO_NAME = "KEY_REPO_NAME"

sealed interface LoadState {
    object Loading: LoadState
    object Error: LoadState
    object Loaded: LoadState
}

data class MainState(
    val userName: String,
    val repoName: String,
    val loadState: LoadState,
    val pullRequests: List<GithubPullRequest>
) {
    companion object {
        fun empty() = MainState(
            userName = "",
            repoName = "",
            loadState = LoadState.Loaded,
            pullRequests = listOf()
        )
    }
}

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val githubRepo: GithubRepo
): ViewModel() {

    private val _state: MutableLiveData<MainState> = MutableLiveData<MainState>(MainState.empty())
    val state: LiveData<MainState> = _state

    private var lastPullRequestsLoadJob: Job? = null

    init {
        val userName = savedStateHandle.get<String>(KEY_USER_NAME)
        val repoName = savedStateHandle.get<String>(KEY_REPO_NAME)

        if (userName != null) {
            updateUserName(userName)
        }

        if (repoName != null) {
            updateRepoName(repoName)
        }
    }

    fun updateUserName(newUserName: String) {
        _state.value = _state.value?.copy(
            userName = newUserName
        )
        refresh()
    }

    fun updateRepoName(newRepoName: String) {
        _state.value = _state.value?.copy(
            repoName = newRepoName
        )
        refresh()
    }

    fun refresh() {
        if (canRefresh()) {
            refreshPullRequests()
        }
    }

    private fun canRefresh(): Boolean {
        return _state.value?.let {
            it.repoName.isNotBlank() && it.userName.isNotBlank()
        } ?: false
    }

    private fun refreshPullRequests() {
        viewModelScope.launch {
            lastPullRequestsLoadJob?.cancelAndJoin()
            delay(500) // debounce
            try {
                _state.value = _state.value?.copy(
                    loadState = LoadState.Loading
                )
                val pullRequests = _state.value?.let { currentState ->
                    githubRepo.getPullRequests(currentState.userName,
                        currentState.repoName, PullRequestState.CLOSED)
                } ?: listOf()
                _state.value = _state.value?.copy(
                    pullRequests = pullRequests,
                    loadState = LoadState.Loaded
                )
            } catch (e: IOException) {
                _state.value = _state.value?.copy(
                    loadState = LoadState.Error
                )
                Log.e("MainViewModel", e.stackTraceToString())
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    loadState = LoadState.Error
                )
                Log.e("MainViewModel", e.stackTraceToString())
            }
        }.let { job ->
            lastPullRequestsLoadJob = job
        }
    }

    override fun onCleared() {
        super.onCleared()
        _state.value?.let {
            savedStateHandle.set(KEY_USER_NAME, it.userName)
            savedStateHandle.set(KEY_REPO_NAME, it.repoName)
        }
    }

    companion object {
        fun provideFactory(
            owner: SavedStateRegistryOwner,
            githubRepo: GithubRepo,
        ): ViewModelProvider.Factory = object: AbstractSavedStateViewModelFactory(owner, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(handle, githubRepo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}