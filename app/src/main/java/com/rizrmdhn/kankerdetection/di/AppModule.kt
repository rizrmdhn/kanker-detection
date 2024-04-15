package com.rizrmdhn.kankerdetection.di

import com.rizrmdhn.kankerdetection.ui.KankerDetectionAppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        KankerDetectionAppViewModel()
    }
}