package com.paulik.professionaldevelopment.data.room.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_test",
    indices = [Index(
        value = arrayOf("word"),
        unique = true
    )]
)
data class Favorite(
    @field:PrimaryKey
    @ColumnInfo(name = "word")
    var word: String,

    @field:ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)
