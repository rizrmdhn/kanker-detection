package com.rizrmdhn.kankerdetection.ui.screen.article

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.domain.model.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ArticleScreenViewModel(
    private val repository: KankerDetectionRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Resource<List<News>>> = MutableStateFlow(Resource.Loading())
    val state: StateFlow<Resource<List<News>>> = _state

    init {
        getNews()
    }

    private fun getNews() {
        _state.value = Resource.Loading()
        viewModelScope.launch {
            repository.getNews().catch {
                _state.value = Resource.Error(it.message.toString())
            }.collect {
                _state.value = it
            }
        }
    }
}