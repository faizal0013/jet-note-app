package com.demo.jetnoteapp.util

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String? = uuid.toString()

    @TypeConverter
    fun uuidFromString(string: String?): UUID? = UUID.fromString(string)
}