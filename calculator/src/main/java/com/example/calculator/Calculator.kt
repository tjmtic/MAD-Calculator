package com.example.calculator

import com.example.calculator.models.CalculatorInput
import kotlinx.coroutines.flow.Flow

interface Calculator {
    val state : Flow<CalculatorState>

    fun addInput(input: CalculatorInput)

    fun clearExpression()

    suspend fun calculate()
}