import com.example.calculator.models.Add
import com.example.calculator.models.CalculatorInput
import com.example.calculator.util.Computations
import com.example.calculator.models.Divide
import com.example.calculator.models.Multiply
import com.example.calculator.models.OperationResult
import com.example.calculator.models.Subtract
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ComputationsTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test2() {
        val result = Computations.computeRPNFromString("3 4 + 5 *") // (3 + 4) * 5 = 35
        if (result != null) {
            assertEquals(35.0, result, 0.0)
        }
    }

    @Test
    fun test3() {
        val result = Computations.computeRPNFromString("3 +") // Invalid expression
        assertNull(result)
    }

    @Test
    fun test4() {
        val result = Computations.computeRPNFromString("10 2 /") // 10 / 2 = 5
        if (result != null) {
            assertEquals(5.0, result, 0.0)
        }
    }

    @Test
    fun test5() {
        val result = Computations.computeRPNFromString("5 1 2 + 4 * + 3 -") // 5 + ((1 + 2) * 4) - 3 = 14
        if (result != null) {
            assertEquals(14.0, result, 0.0)
        }
    }


    //Test Success
    @Test
    fun test6() {
        val expression = listOf(
            CalculatorInput.Number(3.0),
            CalculatorInput.Number(4.0),
            CalculatorInput.Operator(Add),
            CalculatorInput.Number(5.0),
            CalculatorInput.Operator(Multiply)
        )
        val expect = OperationResult.Success(35.0)

        val result = Computations.computeRPN(expression)

        assertEquals(expect, result)
    }

    //Test Failure
    @Test
    fun test7() {
        val expression = listOf(
            CalculatorInput.Number(3.0),
            CalculatorInput.Operator(Add) // Missing second operand
        )
        val expect = OperationResult.Error(cause = IllegalArgumentException("Invalid RPN Expression"))

        val result = Computations.computeRPN(expression)

        assertTrue(result is OperationResult.Error)
        assertEquals(expect.cause.message, (result as OperationResult.Error).cause.message)
    }

    @Test
    fun test8() {
        assertEquals(7.0, Add(3.0, 4.0), 0.0)
        assertEquals(1.0, Subtract(5.0, 4.0), 0.0)
        assertEquals(20.0, Multiply(4.0, 5.0), 0.0)
        assertEquals(2.0, Divide(10.0, 5.0), 0.0)
    }
}