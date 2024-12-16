package com.example.calculator.models

sealed class CalculatorInput {
    data class Input(val value: Int) : CalculatorInput(){
        override fun toString(): String {
            return value.toString()
        }
    }
    data class Number(val value: Double) : CalculatorInput(){
        override fun toString(): String {
            return value.toString()
        }
    }
    data class Operator(val operation: Operation) : CalculatorInput(){
        override fun toString(): String {
            return operation.toString()
        }
    }
}

interface Operation {
    operator fun invoke(a: Double, b: Double): Double
}

object Add : Operation {
    override fun invoke(a: Double, b: Double) = a + b
    override fun toString() = "+"
}

object Subtract : Operation {
    override fun invoke(a: Double, b: Double) = a - b
    override fun toString() = "-"
}

object Multiply : Operation {
    override fun invoke(a: Double, b: Double) = a * b
    override fun toString() = "*"
}

object Divide : Operation {
    override fun invoke(a: Double, b: Double) = a / b
    override fun toString() = "/"
}



sealed class OperationResult {
    data class Success(val data: Any): OperationResult()
    data class Error(val cause: Throwable): OperationResult()
}
