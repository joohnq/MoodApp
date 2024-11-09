package com.joohnq.moodapp.model

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class MyDatabaseInitializer {
    actual fun init(): RoomDatabase.Builder<MyDatabase> {
        val dbFile = documentDirectory() + "/MyDatabase.db"

        return Room.databaseBuilder<MyDatabase>(
            name = dbFile,
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
