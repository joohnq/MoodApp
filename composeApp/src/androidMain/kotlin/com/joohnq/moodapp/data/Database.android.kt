package com.joohnq.moodapp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class MyDatabaseInitializer(
    private val context: Context
) {
    actual fun init(): RoomDatabase.Builder<LocalDatabase> {
        val dbFile = context.getDatabasePath("MyDatabase.db")
        return Room.databaseBuilder<LocalDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath,
        )
    }
}