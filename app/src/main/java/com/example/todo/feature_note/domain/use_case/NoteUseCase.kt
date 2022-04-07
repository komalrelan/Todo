package com.example.todo.feature_note.domain.use_case

data class NoteUseCase(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNotes: AddNotes,
    val getNote: GetNote
)
