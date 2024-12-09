package com.example.snapapp.calculator

import com.example.calculator.models.Add
import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.Divide
import com.example.calculator.models.Multiply
import com.example.calculator.models.Subtract

object CalculatorConstants {
    val INPUTS: List<CalculatorInput.Number> = listOf(
        CalculatorInput.Number(0.0),
        CalculatorInput.Number(1.0),
        CalculatorInput.Number(2.0),
        CalculatorInput.Number(3.0),
        CalculatorInput.Number(4.0),
        CalculatorInput.Number(5.0),
        CalculatorInput.Number(6.0),
        CalculatorInput.Number(7.0),
        CalculatorInput.Number(8.0),
        CalculatorInput.Number(9.0)
    )

    val OPERATIONS: List<CalculatorInput.Operator> = listOf(
        CalculatorInput.Operator(Add),
        CalculatorInput.Operator(Subtract),
        CalculatorInput.Operator(Multiply),
        CalculatorInput.Operator(Divide)
    )
}
