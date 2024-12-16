package com.example.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.example.calculator.models.CalculatorInput

@Entity(
    tableName = "expressions",
    /*foreignKeys = [
        ForeignKey(
            entity = ResultantEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ]*/
)
data class ExpressionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val expression: List<CalculatorInput>?,
    val resultant: Long?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long,
)