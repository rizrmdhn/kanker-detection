package com.rizrmdhn.kankerdetection.di

import androidx.room.Room
import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.data.source.local.LocalDataSource
import com.rizrmdhn.kankerdetection.data.source.local.room.KankerDetectionDatabase
import com.rizrmdhn.kankerdetection.domain.repository.IKankerDetecitonRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<KankerDetectionDatabase>().resultHistoryDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            KankerDetectionDatabase::class.java, "kanker_detection.db"
        ).fallbackToDestructiveMigration().build()
    }
}


val repositoryModule = module {
    single { LocalDataSource(get()) }
    single<IKankerDetecitonRepository> {
        KankerDetectionRepository(get())
    }
}