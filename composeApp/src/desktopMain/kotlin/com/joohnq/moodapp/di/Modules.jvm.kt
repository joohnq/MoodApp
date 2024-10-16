package com.joohnq.moodapp.di

import androidx.room.RoomDatabase
import com.joohnq.moodapp.view.ScreenDimensions
import com.joohnq.moodapp.model.MyDatabase
import com.joohnq.moodapp.model.database.getMyDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf<RoomDatabase.Builder<MyDatabase>>(::getMyDatabase)
    singleOf(::ScreenDimensions)
}