package com.example.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.CalculationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesCalculationsDatabase(
        @ApplicationContext context: Context,
    ): CalculationsDatabase = Room.databaseBuilder(
        context,
        CalculationsDatabase::class.java,
        "calculations-database",
    ).fallbackToDestructiveMigration().build()

}