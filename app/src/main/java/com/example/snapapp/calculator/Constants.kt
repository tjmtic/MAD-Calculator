package com.example.snapapp.calculator

import com.example.calculator.models.Add
import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.Divide
import com.example.calculator.models.Multiply
import com.example.calculator.models.Subtract

object CalculatorConstants {
    val INPUTS: List<CalculatorInput.Input> = listOf(
        CalculatorInput.Input(0),
        CalculatorInput.Input(1),
        CalculatorInput.Input(2),
        CalculatorInput.Input(3),
        CalculatorInput.Input(4),
        CalculatorInput.Input(5),
        CalculatorInput.Input(6),
        CalculatorInput.Input(7),
        CalculatorInput.Input(8),
        CalculatorInput.Input(9)
    )

    val OPERATIONS: List<CalculatorInput.Operator> = listOf(
        CalculatorInput.Operator(Add),
        CalculatorInput.Operator(Subtract),
        CalculatorInput.Operator(Multiply),
        CalculatorInput.Operator(Divide),
    )

    val EXTRAS: List<String> = listOf(
        " ",
        "."
    )
}
