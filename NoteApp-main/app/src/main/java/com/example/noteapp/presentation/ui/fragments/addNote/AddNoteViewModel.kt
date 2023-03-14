package com.example.noteapp.presentation.ui.fragments.addNote

import com.example.noteapp.presentation.ui.utils.base.BaseViewModel
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecases.AddNoteUseCase
import com.example.noteapp.domain.usecases.EditNoteUseCase
import com.example.noteapp.presentation.ui.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    ) : BaseViewModel() {

    private val _addNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val addNoteState = _addNoteState.asStateFlow()

    private val _editNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val editNoteState = _editNoteState.asStateFlow()

    fun addNote(note: Note) {
        addNoteUseCase(note).collectFlow(_addNoteState)
    }

    fun editNote(note: Note){
        editNoteUseCase(note).collectFlow(_addNoteState)
    }
}