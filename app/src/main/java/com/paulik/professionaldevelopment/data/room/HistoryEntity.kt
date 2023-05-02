package com.paulik.professionaldevelopment.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history",
    indices = [Index(
        value = arrayOf("word"),
        unique = true
    )]
)
data class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,

    @field:ColumnInfo(name = "description")
    var description: String?
)
