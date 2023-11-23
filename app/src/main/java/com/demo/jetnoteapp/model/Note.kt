package com.demo.jetnoteapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Entity
data class Note  constructor(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val entryDate: Date = Date.from(Instant.now()),
)
