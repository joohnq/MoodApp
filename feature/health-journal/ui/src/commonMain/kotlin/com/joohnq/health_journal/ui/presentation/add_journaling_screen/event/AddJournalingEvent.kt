package com.joohnq.health_journal.ui.presentation.add_journaling_screen.event

sealed class AddJournalingEvent {
    data object OnGoBack : AddJournalingEvent()
    data object OnAdd : AddJournalingEvent()
}

