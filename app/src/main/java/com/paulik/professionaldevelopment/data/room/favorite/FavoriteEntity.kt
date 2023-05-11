package com.paulik.professionaldevelopment.data.room.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.paulik.professionaldevelopment.data.room.history.HistoryEntity

@Entity(
    tableName = "favorite",
    foreignKeys = [ForeignKey(
        entity = HistoryEntity::class,
        parentColumns = arrayOf("word"),
        childColumns = arrayOf("word"),
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["word"]
)
data class FavoriteEntity(
    @ColumnInfo(name = "word")
    var word: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)
