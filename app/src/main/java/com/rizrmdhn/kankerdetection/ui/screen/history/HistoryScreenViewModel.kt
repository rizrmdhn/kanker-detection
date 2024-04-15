package com.rizrmdhn.kankerdetection.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val kankerDetectionRepository: KankerDetectionRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Resource<List<ResultHistoryEntity>>> =
        MutableStateFlow(Resource.Loading())
    val state: MutableStateFlow<Resource<List<ResultHistoryEntity>>> = _state

    fun getAllHistory() {
        _state.value = Resource.Loading()
        viewModelScope.launch {
            kankerDetectionRepository.getAllHistory().catch {
                _state.value = Resource.Error(it.message.toString())
            }.collect {
                _state.value = it
            }
        }
    }
}