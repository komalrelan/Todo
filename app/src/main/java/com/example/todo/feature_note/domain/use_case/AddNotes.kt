package com.example.todo.feature_note.domain.use_case

import com.example.todo.feature_note.domain.model.InvalidNoteException
import com.example.todo.feature_note.domain.model.Note
import com.example.todo.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNotes(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("the title of note can't be empty")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("the conent of note can't be empty")
        }
        repository.insertNote(note)
    }
}