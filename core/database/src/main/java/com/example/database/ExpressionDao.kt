package com.example.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpressionDao {
    // Create  / Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpression(expression: ExpressionEntity): Long

    // Read
    @Query("SELECT * FROM expressions WHERE id = :id")
    suspend fun getExpressionById(id: Long): ExpressionEntity?

    @Query("SELECT * FROM expressions ORDER BY createdAt DESC")
    suspend fun getAllExpressions(): List<ExpressionEntity>

    // Update
    @Update
    suspend fun updateExpression(expression: ExpressionEntity)

    // Delete
    @Delete
    suspend fun deleteExpression(expression: ExpressionEntity)

    @Query("DELETE FROM expressions WHERE id = :id")
    suspend fun deleteExpressionById(id: Long)

}