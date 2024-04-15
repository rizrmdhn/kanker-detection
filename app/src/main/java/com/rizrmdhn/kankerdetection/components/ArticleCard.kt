package com.rizrmdhn.kankerdetection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.kankerdetection.common.Helpers
import com.rizrmdhn.kankerdetection.domain.model.News
import com.rizrmdhn.kankerdetection.domain.model.Source

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: News,
) {
    val parsedDate = Helpers.parseISODateString(article.publishedAt)
    val formattedDate = Helpers.formatDate(parsedDate)

    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                contentDescription = "Article Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 8.dp,
                            topStart = 8.dp
                        )
                    )
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .background(
                                    shimmerBrush(
                                        targetValue = 1300f,
                                        showShimmer = true
                                    )
                                )
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    min = 150.dp,
                                    max = 200.dp
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    is AsyncImagePainter.State.Success -> {
                        SubcomposeAsyncImageContent()
                    }

                    is AsyncImagePainter.State.Empty -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    min = 150.dp,
                                    max = 200.dp
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.source.name,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = News(
            title = "asdadas",
            description = "asdada",
            urlToImage = "",
            url = "",
            content = "adadas",
            author = "",
            publishedAt = "",
            source = Source(
                id = "",
                name = ""
            )
        )
    )
}