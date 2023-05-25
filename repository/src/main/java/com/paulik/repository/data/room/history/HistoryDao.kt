package com.paulik.repository.room.history

import androidx.room.*
import com.paulik.models.entity.HistoryEntity

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
}
