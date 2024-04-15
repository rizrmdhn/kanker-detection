package com.rizrmdhn.kankerdetection.ui.screen.history

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rizrmdhn.kankerdetection.components.ResultHistoryCard
import com.rizrmdhn.kankerdetection.components.ResultHistoryCardLoader
import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel = koinViewModel(),
    context: Context = LocalContext.current,
) {
    viewModel.state.collectAsState(initial = Resource.Loading()).value.let { state ->
        when (state) {
            is Resource.Loading -> {
                viewModel.getAllHistory()

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    items(10) {
                        ResultHistoryCardLoader()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            is Resource.Success -> {
                HistoryContent(
                    history = state.data ?: emptyList()
                )
            }

            is Resource.Error -> {
                Text(text = state.message.toString())
            }

        }
    }
}

@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    history: List<ResultHistoryEntity>,
) {
    val listState = rememberLazyListState()

    if (history.isEmpty()) {
        Text(text = "No history found")
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(history, key = { it.id }) { item ->
                ResultHistoryCard(
                    history = item
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}