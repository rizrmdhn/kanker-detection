package com.rizrmdhn.kankerdetection.domain.usecase

import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import com.rizrmdhn.kankerdetection.domain.repository.IKankerDetecitonRepository

class KankerDetectionInteractor(
    private val kankerDetectionRepository: IKankerDetecitonRepository
) : KankerDetectionUseCase {
    override fun getAllHistory() = kankerDetectionRepository.getAllHistory()
    override fun getHistoryById(id: Int) = kankerDetectionRepository.getHistoryById(id)
    override suspend fun insertHistory(resultHistory: ResultHistoryEntity) = kankerDetectionRepository.insertHistory(resultHistory)
    override fun deleteHistoryById(id: Int) = kankerDetectionRepository.deleteHistoryById(id)
}