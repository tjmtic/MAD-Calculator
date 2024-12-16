package com.example.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "resultants",
)
data class ResultantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val resultant: Double?,
    val error: String?,
    val createdAt: Long = System.currentTimeMillis(),
)