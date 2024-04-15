package com.rizrmdhn.kankerdetection.data.source.remote

import com.rizrmdhn.kankerdetection.BuildConfig
import com.rizrmdhn.kankerdetection.data.source.remote.network.ApiResponse
import com.rizrmdhn.kankerdetection.data.source.remote.network.ApiService
import com.rizrmdhn.kankerdetection.data.source.remote.response.GetNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(
    private val apiService: ApiService
) {
    fun getNews(): Flow<ApiResponse<GetNewsResponse>> {
        return flow {
            try {
                val response = apiService.getNews(
                    q = "cancer",
                    category = "health",
                    language = "en",
                    apiKey = BuildConfig.NEWS_API_KEY
                )

                if (response.status == "error") {
                    emit(ApiResponse.Error( "Something went wrong"))
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}