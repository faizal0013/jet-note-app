package com.demo.jetnoteapp.repository

import com.demo.jetnoteapp.data.NoteDatabaseDao
import com.demo.jetnoteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)

    suspend fun updateNote(note: Note) = noteDatabaseDao.updateNote(note)

    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)

    suspend fun deleteAllNote() = noteDatabaseDao.deleteAll()

    fun getAllNote(): Flow<List<Note>> = noteDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
}