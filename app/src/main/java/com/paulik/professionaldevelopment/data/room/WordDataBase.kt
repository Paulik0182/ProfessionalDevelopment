package com.paulik.professionaldevelopment.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteDao
import com.paulik.professionaldevelopment.data.room.history.HistoryDao
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity
import com.paulik.professionaldevelopment.domain.entity.HistoryEntity

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
