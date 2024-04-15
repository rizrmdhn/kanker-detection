package com.rizrmdhn.kankerdetection.di

import com.rizrmdhn.kankerdetection.data.KankerDetectionRepository
import com.rizrmdhn.kankerdetection.ui.KankerDetectionAppViewModel
import com.rizrmdhn.kankerdetection.ui.screen.article.ArticleScreenViewModel
import com.rizrmdhn.kankerdetection.ui.screen.history.HistoryScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<KankerDetectionRepository> { KankerDetectionRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel {
        KankerDetectionAppViewModel(get())
    }
    viewModel {
        HistoryScreenViewModel(get())
    }
    viewModel {
        ArticleScreenViewModel(get())
    }
}