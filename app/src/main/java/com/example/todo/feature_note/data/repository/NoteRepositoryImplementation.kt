package com.example.todo.feature_note.data.repository

import com.example.todo.feature_note.data.data_source.NoteDao
import com.example.todo.feature_note.domain.model.Note
import com.example.todo.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(
    private val dao: NoteDao
):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
       return dao.insertNote(note)
    }

    override suspend fun deletNote(note: Note) {
       return dao.deletNote(note)
    }
}