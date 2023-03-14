package com.example.noteapp.presentation.ui.fragments.main

import com.example.noteapp.presentation.ui.utils.base.BaseViewModel
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecases.GetAllNotesUseCase
import com.example.noteapp.domain.usecases.RemoveNoteUseCase
import com.example.noteapp.presentation.ui.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase,
) : BaseViewModel() {

    private val _getAllNotesState = MutableStateFlow<UIState<List<Note>>>(UIState.Empty())
    val getAllNotesState = _getAllNotesState.asStateFlow()

    private val _removeNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val removeNoteState = _removeNoteState.asStateFlow()

    fun getAllNotes() {
        getAllNotesUseCase().collectFlow(_getAllNotesState)
    }

    fun removeNote(note: Note) {
        removeNoteUseCase(note).collectFlow(_removeNoteState)
    }
}