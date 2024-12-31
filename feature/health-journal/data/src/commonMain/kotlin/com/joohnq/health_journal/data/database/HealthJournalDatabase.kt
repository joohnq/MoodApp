package com.joohnq.health_journal.data.database

import com.joohnq.health_journal.data.driver.HealthJournalDriverFactory
import com.joohnq.health_journal.database.HealthJournalDatabaseSql

class HealthJournalDatabase(private val healthJournalDriverFactory: HealthJournalDriverFactory) {
    operator fun invoke(): HealthJournalDatabaseSql =
        HealthJournalDatabaseSql(healthJournalDriverFactory.createDriver())

    companion object {
        const val DATABASE_NAME = "health_journal.db"
    }
}