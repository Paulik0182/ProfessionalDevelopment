package com.paulik.professionaldevelopment.data.room.history

import androidx.room.*
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteEntity

/**
 * suspend - связано с асинхронными функциями, которые могут выполняться на корутинах.
 * suspend указывает на то что функция может быть приостановлена на время, пока она ждет выполнения
 * асинхронной операции, например, запроса к базе данных или сетевому запросу.
 */

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    suspend fun all(): List<HistoryEntity>

    @Query("SELECT * FROM history WHERE word = :word LIMIT 1") // для поиска по словам
    suspend fun getDataByWord(word: String): HistoryEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)

    @Query("SELECT * FROM favorite WHERE is_favorite = 1")
    suspend fun getFavoriteEntities(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE word = :word")
    suspend fun deleteFavorite(word: String)
}
