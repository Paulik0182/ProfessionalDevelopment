package com.paulik.professionaldevelopment.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paulik.professionaldevelopment.data.room.favorite.Favorite
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteDao
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteEntity
import com.paulik.professionaldevelopment.data.room.history.HistoryDao
import com.paulik.professionaldevelopment.data.room.history.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class,
        FavoriteEntity::class,
        Favorite::class
    ],
    version = 1, exportSchema = false
)
abstract class WordDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun favoriteDao(): FavoriteDao
}
