package com.rizrmdhn.kankerdetection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultHistoryCardLoader(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier.padding(16.dp)
        ) {
            Box(
                modifier = modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = true
                        )
                    )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(
                    modifier = modifier
                        .width(200.dp)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = modifier
                        .width(200.dp)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = modifier
                        .width(200.dp)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = modifier
                        .width(200.dp)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun ResultHistoryCardLoaderPreview() {
    ResultHistoryCardLoader()
}