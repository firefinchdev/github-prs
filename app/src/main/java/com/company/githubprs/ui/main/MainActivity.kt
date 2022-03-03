package com.company.githubprs.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.company.githubprs.Injection
import com.company.githubprs.R
import com.company.githubprs.extensions.toHumanReadableDate
import com.company.githubprs.models.GithubPullRequest
import com.company.githubprs.models.PullRequestState
import com.company.githubprs.ui.composables.*
import com.company.githubprs.ui.theme.GithubPRsTheme
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.time.temporal.TemporalField
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        Injection.provideMainViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubPRsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state
        .observeAsState(MainState.empty())
    Column(Modifier.fillMaxSize()) {
        UserTextField(
            value = state.userName,
            onValueChange = { viewModel.updateUserName(it) },
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            hint = stringResource(R.string.input_hint_user_name)
        )
        UserTextField(
            value = state.repoName,
            onValueChange = { viewModel.updateRepoName(it) },
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            hint = stringResource(R.string.input_hint_repo_name)
        )
        AnimatedVisibility(state.loadState is LoadState.Error) {
            ErrorBanner(
                Modifier
                    .height(56.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            )
        }
        AnimatedVisibility(state.loadState is LoadState.Loading) {
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        if (state.loadState is LoadState.Loaded) {
            if (state.pullRequests.isNotEmpty()) {
                LazyColumn() {
                    items(state.pullRequests,
                        key = { it.id }
                    ) { pullRequest ->
                        PullRequestItem(pullRequest)
                    }
                }
            } else {
                EmptyScreen(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }
}
