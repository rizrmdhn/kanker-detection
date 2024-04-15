package com.rizrmdhn.kankerdetection.ui.screen.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.kankerdetection.common.Helpers
import com.rizrmdhn.kankerdetection.components.shimmerBrush


@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    uri: Uri,
    setImageUri: (Uri) -> Unit,
    analyzingResult: Boolean,
    analyzeImage: () -> Unit,
) {


    HomeContent(
        context = context,
        imageUri = uri,
        setImageUri = setImageUri,
        analyzingResult = analyzingResult,
        analyzingImage = analyzeImage
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    context: Context,
    imageUri: Uri,
    setImageUri: (Uri) -> Unit,
    analyzingResult: Boolean,
    analyzingImage: () -> Unit
) {
    val uri = Helpers.getImageUri(
        context = context,
    )

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            setImageUri(uri)
        } else {
            Toast.makeText(context, "Failed to take picture", Toast.LENGTH_SHORT).show()
            setImageUri(Uri.EMPTY)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            setImageUri(it)
        } else {
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show()
            setImageUri(Uri.EMPTY)
        }
    }

    val permissionCameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionGalleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUri != Uri.EMPTY) {
            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = "Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 150.dp,
                        max = 200.dp
                    )
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

        } else {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Image Selected")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextButton(
                onClick = {
                    val permissionCheckResult =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_MEDIA_IMAGES
                            )
                        } else {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        galleryLauncher.launch("image/*")
                    } else {
                        // Request a permission
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionGalleryLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionGalleryLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                },
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                Text(
                    text = "Add Image from Gallery",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        // Request a permission
                        permissionCameraLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                Text(
                    text = "Add Image from Camera",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (imageUri != Uri.EMPTY) {
            TextButton(
                onClick = {
                    if (!analyzingResult) {
                        analyzingImage()
                    }
                },
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .width(200.dp)
                    .background(
                        color = if (analyzingResult) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
            ) {
                if (analyzingResult) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "Analyze Image",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeContent(
        context = LocalContext.current,
        imageUri = Uri.EMPTY,
        setImageUri = {},
        analyzingResult = false,
        analyzingImage = {}
    )
}