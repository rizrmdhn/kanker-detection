package com.rizrmdhn.kankerdetection.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizrmdhn.kankerdetection.data.source.local.entity.ResultHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultHistoryDao {
    @Query("SELECT * FROM result_history")
    fun getAll(): Flow<List<ResultHistoryEntity>>

    @Query("SELECT * FROM result_history WHERE id = :id")
    fun getById(id: Int): Flow<ResultHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultHistoryEntity: ResultHistoryEntity)

    @Query("DELETE FROM result_history WHERE id = :id")
    fun deleteById(id: Int)
}