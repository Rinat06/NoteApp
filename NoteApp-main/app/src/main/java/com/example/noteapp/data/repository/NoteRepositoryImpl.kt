package com.example.noteapp.data.repository

import com.example.noteapp.data.mappers.toNote
import com.example.noteapp.data.mappers.toNoteEntity
import com.example.noteapp.data.local.NoteDao
import com.example.noteapp.data.base.BaseRepository
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
    ) : NoteRepository, BaseRepository() {

    override fun addNote(note: Note) = doRequest {
        noteDao.addNote(note.toNoteEntity())
    }

    override fun getAllNotes() = doRequest {
        noteDao.getAllNotes().map { it.toNote() }
    }

    override fun editNote(note: Note) = doRequest {
        noteDao.editNote(note.toNoteEntity())
    }

    override fun removeNote(note: Note) = doRequest {
        noteDao.removeNote(note.toNoteEntity())
    }
}