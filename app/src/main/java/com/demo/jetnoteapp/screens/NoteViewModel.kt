package com.demo.jetnoteapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.jetnoteapp.data.NoteDataSource
import com.demo.jetnoteapp.model.Note
import com.demo.jetnoteapp.repository.NotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NotRepository) : ViewModel(){

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())

    public val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository
                .getAllNote()
                .distinctUntilChanged()
                .collect { listOfNotes ->
                    if(!listOfNotes.isNullOrEmpty()){
                        _noteList.value = listOfNotes
                    }
                }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }

    fun updateNote(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }

    fun removeNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }
}