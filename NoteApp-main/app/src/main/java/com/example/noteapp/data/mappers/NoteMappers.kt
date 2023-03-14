package com.example.noteapp.data.mappers

import com.example.noteapp.data.model.NoteEntity
import com.example.noteapp.domain.model.Note


fun NoteEntity.toNote() = Note(id, title, description)

fun Note.toNoteEntity(): NoteEntity = NoteEntity(id, title, description)