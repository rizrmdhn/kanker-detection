package com.rizrmdhn.kankerdetection.ui.screen.article

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
import androidx.compose.ui.unit.dp
import com.rizrmdhn.kankerdetection.components.ArticleCard
import com.rizrmdhn.kankerdetection.components.ArticleCardLoader
import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.domain.model.News
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArticleScreen(
    viewModel: ArticleScreenViewModel = koinViewModel(),
) {
    viewModel.state.collectAsState(initial = Resource.Loading()).value.let {
        when (it) {
            is Resource.Loading -> {
                // Loading state

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    items(10) {
                        ArticleCardLoader()
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }

            is Resource.Success -> {
                it.data?.let { it1 ->
                    ArticleScreenContent(
                        article = it1
                    )
                }
            }

            is Resource.Error -> {
                // Error state
                Text(text = it.message.toString())
            }
        }
    }
}

@Composable
fun ArticleScreenContent(
    article: List<News>
) {
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(article) { item ->
            ArticleCard(
                article = item
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}