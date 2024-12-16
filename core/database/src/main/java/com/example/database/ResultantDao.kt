package com.example.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ResultantDao {
    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResultant(resultant: ResultantEntity): Long

    // Read
    @Query("SELECT * FROM resultants WHERE id = :id")
    suspend fun getResultantById(id: Long): ResultantEntity?


    @Query("SELECT * FROM resultants ORDER BY createdAt DESC")
    suspend fun getAllResultants(): List<ResultantEntity>

    // Update
    @Update
    suspend fun updateResultant(resultant: ResultantEntity)

    // Delete
    @Delete
    suspend fun deleteResultant(resultant: ResultantEntity)

    @Query("DELETE FROM resultants WHERE id = :id")
    suspend fun deleteResultantById(id: Long)
}