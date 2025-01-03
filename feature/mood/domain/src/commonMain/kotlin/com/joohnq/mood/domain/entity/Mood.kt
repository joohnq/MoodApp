package com.joohnq.mood.domain.entity

import com.joohnq.mood.domain.MoodProperties

sealed class Mood(
    override val id: Int,
    override val healthLevel: Int,
) : MoodProperties {
    data object Depressed : Mood(id = DEPRESSED.id, healthLevel = DEPRESSED.healthLevel)
    data object Sad : Mood(id = SAD.id, healthLevel = SAD.healthLevel)
    data object Neutral : Mood(id = NEUTRAL.id, healthLevel = NEUTRAL.healthLevel)
    data object Happy : Mood(id = HAPPY.id, healthLevel = HAPPY.healthLevel)
    data object Overjoyed : Mood(id = OVERJOYED.id, healthLevel = OVERJOYED.healthLevel)

    companion object {
        val DEPRESSED = DMoodProperties(0, 20)
        val SAD = DMoodProperties(1, 40)
        val NEUTRAL = DMoodProperties(2, 60)
        val HAPPY = DMoodProperties(3, 80)
        val OVERJOYED = DMoodProperties(4, 100)

        fun toValue(src: Int): Mood = when (src) {
            DEPRESSED.id -> Depressed
            SAD.id -> Sad
            NEUTRAL.id -> Neutral
            HAPPY.id -> Happy
            OVERJOYED.id -> Overjoyed
            else -> throw IllegalArgumentException("Unknown mood: $src")
        }

        fun Mood?.fromValue(): Int = this?.id ?: -1

        fun getAll(): List<Mood> = listOf(
            Depressed,
            Sad,
            Neutral,
            Happy,
            Overjoyed
        )
    }
}
