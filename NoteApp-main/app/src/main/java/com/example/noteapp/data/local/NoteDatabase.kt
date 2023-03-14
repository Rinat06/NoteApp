package com.example.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.data.model.NoteEntity

@Database(entities = [NoteEntity :: class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao
}

