package com.rizrmdhn.kankerdetection.data

import com.rizrmdhn.kankerdetection.data.source.local.LocalDataSource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.data.source.remote.RemoteDataSource
import com.rizrmdhn.kankerdetection.data.source.remote.network.ApiResponse
import com.rizrmdhn.kankerdetection.domain.model.News
import com.rizrmdhn.kankerdetection.domain.repository.IKankerDetecitonRepository
import com.rizrmdhn.kankerdetection.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class KankerDetectionRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IKankerDetecitonRepository {
    override fun getAllHistory(): Flow<Resource<List<ResultHistoryEntity>>> {
        return flow {
            emit(Resource.Loading())
            try {
                localDataSource.getAllHistory().collect() {
                    emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }

        }
    }

    override fun getHistoryById(id: Int): Flow<Resource<ResultHistoryEntity>> {
        return flow {
            emit(Resource.Loading())
            try {
                localDataSource.getHistoryById(id).collect {
                    emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override suspend fun insertHistory(resultHistory: ResultHistoryEntity) =
        localDataSource.insertHistory(resultHistory)

    override fun deleteHistoryById(id: Int) = localDataSource.deleteHistoryById(id)

    override fun getNews(): Flow<Resource<List<News>>> {
        return flow {
            remoteDataSource.getNews().collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> {
                        val newsList = DataMapper.mapResponseToDomain(apiResponse.data.articles)
                        emit(Resource.Success(newsList))
                    }

                    is ApiResponse.Error -> {
                        emit(Resource.Error(apiResponse.errorMessage))
                    }

                    is ApiResponse.Empty -> {
                        emit(Resource.Error("Empty"))
                    }
                }
            }
        }
    }
}