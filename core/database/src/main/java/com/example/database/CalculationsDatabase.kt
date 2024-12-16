package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.database.ExpressionDao
import com.example.core.database.ExpressionEntity
import com.example.core.database.ResultantDao
import com.example.core.database.ResultantEntity
import com.example.core.database.mapper.ExpressionMapper

@Database(
    entities = [
        ExpressionEntity::class,
        ResultantEntity::class,
    ],
    version = 1,

    exportSchema = true,
)
@TypeConverters(
    ExpressionMapper::class,
)
abstract class CalculationsDatabase : RoomDatabase() {
    abstract fun resultantDao(): ResultantDao
    abstract fun expressionDao(): ExpressionDao
}
