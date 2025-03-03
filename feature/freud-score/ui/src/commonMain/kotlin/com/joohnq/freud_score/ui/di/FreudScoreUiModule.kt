package com.joohnq.freud_score.ui.di

import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val freudScoreUiModule: Module = module {
    singleOf(::FreudScoreViewModel)
}