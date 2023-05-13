package com.paulik.professionaldevelopment.data.room.favorite

import androidx.room.*
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    suspend fun all(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE is_favorite = 1")
    suspend fun getFavoriteEntities(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE word = :word LIMIT 1")
    suspend fun getFavoriteWord(word: String): FavoriteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE word = :word")
    suspend fun deleteFavorite(word: String)

    @Update
    suspend fun update(entity: FavoriteEntity)

    @Delete
    suspend fun delete(entity: FavoriteEntity)
}