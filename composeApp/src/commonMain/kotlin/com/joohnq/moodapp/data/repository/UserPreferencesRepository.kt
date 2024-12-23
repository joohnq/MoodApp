package com.joohnq.moodapp.data.repository

import com.joohnq.moodapp.domain.UserPreferences
import com.joohnq.moodapp.data.dao.UserPreferencesDAO

interface UserPreferencesRepository {
    suspend fun getUserPreferences(): UserPreferences
    suspend fun addUserPreferences(userPreferences: UserPreferences): Boolean
    suspend fun insertUserPreferences(): Boolean
    suspend fun updateSkipWelcomeScreen(value: Boolean): Boolean
    suspend fun updateSkipOnboardingScreen(value: Boolean): Boolean
    suspend fun updateSkipGetUserNameScreen(value: Boolean): Boolean
}

class UserPreferencesRepositoryImpl(private val userPreferencesDAO: UserPreferencesDAO) :
    UserPreferencesRepository {
    override suspend fun getUserPreferences(): UserPreferences =
        userPreferencesDAO.getUserPreferences()

    override suspend fun addUserPreferences(userPreferences: UserPreferences): Boolean =
        try {
            userPreferencesDAO.addUserPreferences(userPreferences)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun insertUserPreferences(): Boolean =
        try {
            userPreferencesDAO.addUserPreferences()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun updateSkipWelcomeScreen(value: Boolean): Boolean =
        try {
            userPreferencesDAO.updateSkipWelcomeScreen(value)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun updateSkipOnboardingScreen(value: Boolean): Boolean =
        try {
            userPreferencesDAO.updateSkipOnboardingScreen(value)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun updateSkipGetUserNameScreen(value: Boolean): Boolean =
        try {
            userPreferencesDAO.updateSkipGetUserNameScreen(value)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
}