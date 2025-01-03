package com.joohnq.freud_score.ui.di

import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val freudScoreUiModule = module {
    viewModelOf(::FreudScoreViewModel)
}