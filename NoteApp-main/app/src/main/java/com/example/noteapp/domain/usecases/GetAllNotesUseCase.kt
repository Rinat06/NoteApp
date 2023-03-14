package com.example.noteapp.domain.usecases

import com.example.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    operator fun invoke() = noteRepository.getAllNotes()
}