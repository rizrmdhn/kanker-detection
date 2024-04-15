package com.rizrmdhn.kankerdetection.data

import com.rizrmdhn.kankerdetection.data.source.local.LocalDataSource
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.domain.repository.IKankerDetecitonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KankerDetectionRepository(
    private val localDataSource: LocalDataSource,
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
}