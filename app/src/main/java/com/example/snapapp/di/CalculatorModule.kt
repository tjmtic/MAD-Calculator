package com.example.snapapp.di

import com.example.calculator.Calculator
import com.example.calculator.CalculatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CalculatorModule {

    @Provides
    fun provideCalculator(): Calculator {
        return CalculatorImpl()
    }

}