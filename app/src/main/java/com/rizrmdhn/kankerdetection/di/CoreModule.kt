package com.rizrmdhn.kankerdetection.di

import androidx.room.Room
import com.rizrmdhn.kankerdetection.BuildConfig
import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.data.source.local.LocalDataSource
import com.rizrmdhn.kankerdetection.data.source.local.room.KankerDetectionDatabase
import com.rizrmdhn.kankerdetection.data.source.remote.RemoteDataSource
import com.rizrmdhn.kankerdetection.data.source.remote.network.ApiService
import com.rizrmdhn.kankerdetection.domain.repository.IKankerDetecitonRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<KankerDetectionDatabase>().resultHistoryDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            KankerDetectionDatabase::class.java, "kanker_detection.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
                .build()
            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        listOf(authInterceptor)

        OkHttpClient.Builder()
            .addInterceptor(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.NONE
                )
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
        retrofit.create(ApiService::class.java)
    }
}


val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IKankerDetecitonRepository> {
        KankerDetectionRepository(get(), get())
    }
}