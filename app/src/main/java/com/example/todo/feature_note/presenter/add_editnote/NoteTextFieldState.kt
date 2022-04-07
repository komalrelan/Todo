package com.example.todo.feature_note.presenter.add_editnote

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
