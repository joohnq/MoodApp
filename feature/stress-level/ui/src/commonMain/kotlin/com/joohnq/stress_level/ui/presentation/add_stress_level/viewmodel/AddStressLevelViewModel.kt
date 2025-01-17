package com.joohnq.stress_level.ui.presentation.add_stress_level.viewmodel

import androidx.lifecycle.ViewModel
import com.joohnq.shared_resources.util.mappers.toggle
import com.joohnq.stress_level.ui.mapper.fromSliderValueToStressLevelResource
import com.joohnq.stress_level.ui.resource.StressorResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddStressLevelViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddingStressLevelViewModelState())
    val state: StateFlow<AddingStressLevelViewModelState> =
        _state.asStateFlow()

    fun onAction(intent: AddStressLevelIntent) {
        when (intent) {
            is AddStressLevelIntent.UpdateAddingOtherValue -> updateAddingOtherValue(intent.value)
            is AddStressLevelIntent.UpdateAddingOtherValueError ->
                updateAddingOtherValueError(intent.error)

            is AddStressLevelIntent.UpdateAddingStressors -> updateAddingStressStressors(intent.stressor)
            is AddStressLevelIntent.UpdateAddingSliderValue -> updateAddingSliderValue(intent.sliderValue)
            is AddStressLevelIntent.ResetState -> resetState()
        }
    }


    private fun updateAddingSliderValue(value: Float) {
        _state.update {
            it.copy(
                sliderValue = value,
                stressLevel = value.fromSliderValueToStressLevelResource()
            )
        }
    }

    private fun updateAddingStressStressors(stressor: StressorResource) {
        _state.update {
            it.copy(stressors = state.value.stressors.toggle(stressor))
        }
    }

    private fun updateAddingOtherValue(otherValue: String) {
        _state.update {
            it.copy(otherValue = otherValue, otherValueError = null)
        }
    }

    private fun updateAddingOtherValueError(otherValueError: String?) {
        _state.update {
            it.copy(otherValueError = otherValueError)
        }
    }

    private fun resetState() {
        _state.update { AddingStressLevelViewModelState() }
    }
}