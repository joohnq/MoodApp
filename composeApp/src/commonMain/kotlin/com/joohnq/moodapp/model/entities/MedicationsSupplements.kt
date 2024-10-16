package com.joohnq.moodapp.model.entities

import androidx.compose.runtime.saveable.Saver
import com.joohnq.moodapp.view.constants.Drawables
import com.joohnq.moodapp.view.entities.IconProps
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.im_not_taking_any
import moodapp.composeapp.generated.resources.over_the_counter_supplements
import moodapp.composeapp.generated.resources.prefer_not_to_say
import moodapp.composeapp.generated.resources.prescribed_medications
import org.jetbrains.compose.resources.StringResource

@Serializable
sealed class MedicationsSupplements(
    val id: String,
    @Contextual val text: StringResource,
    @Contextual val icon: IconProps
) {
    @Serializable
    data object PrescribedMedications : MedicationsSupplements(
        id = PRESCRIBED_MEDICATIONS,
        text = Res.string.prescribed_medications,
        icon = IconProps(
            icon = Drawables.Icons.Medicine,
        )
    )

    @Serializable
    data object OverTheCounterSupplements : MedicationsSupplements(
        id = OVER_THE_COUNTER_SUPPLEMENTS,
        text = Res.string.over_the_counter_supplements,
        icon = IconProps(icon = Drawables.Icons.DrugStore)
    )

    @Serializable
    data object ImNotTakingAny : MedicationsSupplements(
        id = IM_NOT_TAKING_ANY,
        text = Res.string.im_not_taking_any,
        icon = IconProps(icon = Drawables.Icons.Nothing)
    )

    @Serializable
    data object PreferNotToSay :
        MedicationsSupplements(
            id = PREFER_NOT_TO_SAY,
            text = Res.string.prefer_not_to_say,
            icon = IconProps(icon = Drawables.Icons.Close)
        )

    companion object {
        const val PRESCRIBED_MEDICATIONS = "0"
        const val OVER_THE_COUNTER_SUPPLEMENTS = "1"
        const val IM_NOT_TAKING_ANY = "2"
        const val PREFER_NOT_TO_SAY = "3"

        fun toValue(src: String): MedicationsSupplements = when (src) {
            PRESCRIBED_MEDICATIONS -> PrescribedMedications
            OVER_THE_COUNTER_SUPPLEMENTS -> OverTheCounterSupplements
            IM_NOT_TAKING_ANY -> ImNotTakingAny
            PREFER_NOT_TO_SAY -> PreferNotToSay
            else -> throw IllegalArgumentException("Unknown medications supplements option: $src")
        }

        fun fromValue(medicationsSupplements: MedicationsSupplements?): String =
            medicationsSupplements?.id.toString()

        fun getAll(): List<MedicationsSupplements> = listOf(
            PrescribedMedications,
            OverTheCounterSupplements,
            ImNotTakingAny,
            PreferNotToSay
        )

        fun getSaver(): Saver<MedicationsSupplements?, String> = Saver(
            save = { fromValue(it) },
            restore = { toValue(it) }
        )
    }
}
