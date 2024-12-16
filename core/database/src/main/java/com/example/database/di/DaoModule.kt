package com.example.core.database.di

import com.example.core.database.ExpressionDao
import com.example.core.database.ResultantDao
import com.example.database.CalculationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideResultantDao(db: CalculationsDatabase): ResultantDao {
        return db.resultantDao()
    }

    @Provides
    fun provideExpressionDao(db: CalculationsDatabase): ExpressionDao {
        return db.expressionDao()
    }
}