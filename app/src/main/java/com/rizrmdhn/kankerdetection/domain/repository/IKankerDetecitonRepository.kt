package com.rizrmdhn.kankerdetection.domain.repository

import com.rizrmdhn.kankerdetection.data.Resource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.domain.model.News
import kotlinx.coroutines.flow.Flow

interface IKankerDetecitonRepository {
    fun getAllHistory(): Flow<Resource<List<ResultHistoryEntity>>>
    fun getHistoryById(id: Int): Flow<Resource<ResultHistoryEntity>>
    suspend fun insertHistory(resultHistory: ResultHistoryEntity)
    fun deleteHistoryById(id: Int)

    fun getNews(): Flow<Resource<List<News>>>
}