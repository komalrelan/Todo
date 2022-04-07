package com.example.todo.feature_note.presenter.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.feature_note.domain.model.Note
import com.example.todo.feature_note.domain.use_case.NoteUseCase
import com.example.todo.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesviewModel @Inject constructor(
private val noteUseCase: NoteUseCase
):ViewModel(){
    private val _state= mutableStateOf<NoteState>(NoteState())
    val state: State<NoteState> =_state
    private var recentlyDeletedNote :Note? =null
    private var getNotesJob: Job?=null


    fun onEvent(event: NotesEvent){
     when(event){

        is NotesEvent.Order ->{
            if(state.value.noteOrder::class== event.noteOrder::class &&
                    state.value.noteOrder.orderType== event.noteOrder.orderType
            ){
                return
            }
            getNotes(event.noteOrder)

        }is NotesEvent.DeleteNote ->{
            viewModelScope.launch {
                noteUseCase.deleteNote(event.note)
                recentlyDeletedNote=event.note
            }

     }is NotesEvent.RestoreNote ->{

         viewModelScope.launch {
            noteUseCase.addNotes(recentlyDeletedNote?: return@launch )
             recentlyDeletedNote=null
         }

     }is NotesEvent.ToggleOrderSection ->{
        _state.value= state.value.copy(
            isSelectionVisible = !state.value.isSelectionVisible
        )
     }
     }
 }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob= noteUseCase.getNotes(noteOrder)
            .onEach { notes ->
                _state.value= state.value.copy(
                    notes=notes,
                noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)


    }
}