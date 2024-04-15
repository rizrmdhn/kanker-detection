package com.rizrmdhn.kankerdetection.ui

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.kankerdetection.common.Helpers
import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.utils.ImageClassifierHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.time.Instant

class KankerDetectionAppViewModel(
    private val repository: KankerDetectionRepository,
) : ViewModel() {
    private val _uri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val uri: StateFlow<Uri> = _uri

    private val _classifications: MutableStateFlow<List<Classifications>> =
        MutableStateFlow(emptyList())
    val classifications: StateFlow<List<Classifications>> = _classifications

    private val _analyzingResult: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val analyzingResult: StateFlow<Boolean> = _analyzingResult

    fun setUri(uri: Uri) {
        _uri.value = uri
    }

    fun setAnalyzingResult(result: Boolean) {
        _analyzingResult.value = result
    }

    fun analyzeImage(
        context: Context,
        navigateToResult: () -> Unit
    ) {
        setAnalyzingResult(true)

        viewModelScope.launch {
            // Do the image processing here
            val imageClassifier = ImageClassifierHelper(
                context = context,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onResult(results: List<Classifications>?) {
                        setAnalyzingResult(false)
                        _classifications.value = results?.get(0)?.let { listOf(it) } ?: emptyList()
                        navigateToResult()

                        viewModelScope.launch {
                            Helpers.uriToBlob(context, uri.value)?.let {
                                ResultHistoryEntity(
                                    imageUri = it,
                                    result = results.toString(),
                                    createdAt = Instant.now().toString(),
                                )
                            }?.let {
                                repository.insertHistory(
                                    it
                                )
                            }
                        }
                    }

                    override fun onError(error: String) {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        setAnalyzingResult(false)
                    }
                }
            )

            imageClassifier.classifyStaticImage(uri.value)
        }
    }
}