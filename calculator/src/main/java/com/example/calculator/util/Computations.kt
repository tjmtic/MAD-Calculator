package com.example.calculator.util

import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.OperationResult

object Computations {
    //Pure Kotlin Operation  Functionality
    internal fun computeRPN(expression: List<CalculatorInput>): OperationResult {
        val stack = ArrayDeque<Double>()

        try {
            for (input in expression) {
                when (input) {
                    is CalculatorInput.Number -> stack.addLast(input.value)
                    is CalculatorInput.Operator -> {
                        if (stack.size < 2) {
                            throw IllegalArgumentException("Invalid RPN Expression")
                        }
                        val b = stack.removeLast()
                        val a = stack.removeLast()
                        stack.addLast(input.operation(a, b))
                    }
                }
            }

            if (stack.size == 1) {
                return OperationResult.Success(stack.last())
            }
            else {
                throw IllegalArgumentException("Invalid RPN Expression")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return OperationResult.Error(e)
        }
    }


    //String Based Implementation
    // Compute the result of an RPN expression
    fun computeRPNFromString(expression: String): Double? {
        val tokens = expression.split(" ")
        val stack = ArrayDeque<Double>()

        try {
            for (token in tokens) {
                when {
                    token.isNumber() -> stack.addLast(token.toDouble())
                    token.isOperator() -> {
                        if (stack.size < 2) throw IllegalArgumentException("Invalid RPN Expression")
                        val b = stack.removeLast()
                        val a = stack.removeLast()
                        stack.addLast(applyOperator(a, b, token))
                    }
                    else -> throw IllegalArgumentException("Unknown token: $token")
                }
            }

            // The result should be the only item left in the stack
            return if (stack.size == 1) stack.first() else throw IllegalArgumentException("Invalid RPN Expression")
        } catch (e: Exception) {
            e.printStackTrace()
            return null // Return null if the computation fails
        }
    }

    // Helper to apply operators
    private fun applyOperator(a: Double, b: Double, operator: String): Double {
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw IllegalArgumentException("Unsupported operator: $operator")
        }
    }

    // Helper to check if a string is a number
    private fun String.isNumber(): Boolean {
        return this.toDoubleOrNull() != null
    }

    // Helper to check if a string is an operator
    private fun String.isOperator(): Boolean {
        return this in setOf("+", "-", "*", "/")
    }
}