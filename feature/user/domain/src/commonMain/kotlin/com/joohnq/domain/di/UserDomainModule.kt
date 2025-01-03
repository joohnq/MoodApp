package com.joohnq.domain.di

import com.joohnq.domain.use_case.user.AddUserUseCase
import com.joohnq.domain.use_case.user.GetUserUseCase
import com.joohnq.domain.use_case.user.InitUserUseCase
import com.joohnq.domain.use_case.user.UpdateMedicationsSupplementsUseCase
import com.joohnq.domain.use_case.user.UpdatePhysicalSymptomsUseCase
import com.joohnq.domain.use_case.user.UpdateSoughtHelpUseCase
import com.joohnq.domain.use_case.user.UpdateUserNameUseCase
import com.joohnq.domain.use_case.user.UpdateUserUseCase
import com.joohnq.domain.use_case.user_preferences.AddUserPreferencesUseCase
import com.joohnq.domain.use_case.user_preferences.GetUserPreferencesUseCase
import com.joohnq.domain.use_case.user_preferences.InsertUserPreferencesUseCase
import com.joohnq.domain.use_case.user_preferences.UpdateSkipGetUserNameScreenUseCase
import com.joohnq.domain.use_case.user_preferences.UpdateSkipOnboardingScreenUseCase
import com.joohnq.domain.use_case.user_preferences.UpdateSkipWelcomeScreenUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val userDomainModule = module {
    factoryOf(::AddUserUseCase)
    factoryOf(::GetUserUseCase)
    factoryOf(::InitUserUseCase)
    factoryOf(::UpdateMedicationsSupplementsUseCase)
    factoryOf(::UpdatePhysicalSymptomsUseCase)
    factoryOf(::UpdateSoughtHelpUseCase)
    factoryOf(::UpdateUserNameUseCase)
    factoryOf(::UpdateUserUseCase)
    factoryOf(::AddUserPreferencesUseCase)
    factoryOf(::GetUserPreferencesUseCase)
    factoryOf(::InsertUserPreferencesUseCase)
    factoryOf(::UpdateSkipGetUserNameScreenUseCase)
    factoryOf(::UpdateSkipOnboardingScreenUseCase)
    factoryOf(::UpdateSkipWelcomeScreenUseCase)
}