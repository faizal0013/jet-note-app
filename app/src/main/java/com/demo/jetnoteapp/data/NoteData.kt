package com.demo.jetnoteapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.demo.jetnoteapp.model.Note

class NoteDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadNote(): List<Note>{
        return listOf(
            Note(title = "A Good Day", description = "description")
        )
    }
}