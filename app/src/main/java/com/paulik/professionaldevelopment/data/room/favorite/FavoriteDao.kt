package com.paulik.professionaldevelopment.data.room.favorite

import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_test")
    suspend fun all(): List<Favorite>


    @Query("SELECT * FROM favorite_test WHERE is_favorite = 1")
    suspend fun getFavoriteEntities(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_test WHERE word = :word")
    suspend fun deleteFavorite(word: String)
}