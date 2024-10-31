package com.joohnq.moodapp.view.screens

import kotlinx.serialization.Serializable

object Screens {
    @Serializable
    object OnboardingGraph{
        @Serializable
        object ExpressionAnalysisScreen

        @Serializable
        object MedicationsSupplementsScreen

        @Serializable
        object MoodRateScreen

        @Serializable
        object PhysicalSymptomsScreen

        @Serializable
        object ProfessionalHelpScreen

        @Serializable
        object SleepQualityScreen

        @Serializable
        object StressRateScreen
    }

    @Serializable
    object LoadingScreen

    @Serializable
    object WelcomeScreen

    @Serializable
    object GetUserNameScreen

    @Serializable
    object CompilingDataScreen

    @Serializable
    object HomeGraph{
        @Serializable
        object HomeScreen

        @Serializable
        object AddMoodScreen

        @Serializable
        object JournalingScreen

        @Serializable
        object FreudScoreScreen

        @Serializable
        object MoodScreen

        @Serializable
        object HealthJournalScreen
    }
}