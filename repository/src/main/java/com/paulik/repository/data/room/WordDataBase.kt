package com.paulik.repository.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paulik.models.entity.FavoriteEntity
import com.paulik.models.entity.HistoryEntity
import com.paulik.repository.room.favorite.FavoriteDao
import com.paulik.repository.room.history.HistoryDao

@Database(
    entities = [
        HistoryEntity::class,
        FavoriteEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class WordDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun favoriteDao(): FavoriteDao
}
