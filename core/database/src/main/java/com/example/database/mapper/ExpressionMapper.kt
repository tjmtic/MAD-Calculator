package com.example.database.mapper

import androidx.room.TypeConverter
import com.example.calculator.models.Add
import com.example.calculator.models.CalculatorInput

class ExpressionMapper {
    @TypeConverter
    fun expressionListToString(value: List<CalculatorInput>): String =
            value.joinToString(" "){it.toString()}

    @TypeConverter
    fun stringToExpressionList(value: String): List<CalculatorInput> =
        value.split(" ").map{
            when(it){
                "0" -> CalculatorInput.Number(0.0)
                else -> CalculatorInput.Operator(Add)
            }
        }

}