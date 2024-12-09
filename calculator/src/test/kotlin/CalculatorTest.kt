import com.example.calculator.models.Add
import com.example.calculator.CalculatorImpl
import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.Divide
import com.example.calculator.models.Multiply
import com.example.calculator.models.Subtract
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private lateinit var calculator: CalculatorImpl

    @Before
    fun setup() {
        calculator = CalculatorImpl()
    }

    @Test
    fun test1() = runTest {
        calculator.addInput(CalculatorInput.Number(3.0))
        calculator.addInput(CalculatorInput.Number(4.0))
        calculator.addInput(CalculatorInput.Operator(Add))

        val expression = calculator.state.first().inputs
        assertEquals(3.0, (expression[0] as CalculatorInput.Number).value, 0.0)
        assertEquals(4.0, (expression[1] as CalculatorInput.Number).value, 0.0)
        assertEquals(Add, (expression[2] as CalculatorInput.Operator).operation)
    }

    @Test
    fun test2() = runTest {
        calculator.addInput(CalculatorInput.Number(3.0))
        calculator.addInput(CalculatorInput.Number(4.0))
        calculator.addInput(CalculatorInput.Operator(Add))
        calculator.calculate()

        val result = calculator.state.first().result
        if (result != null) {
            assertEquals(7.0, result, 0.0)
        }
    }

    @Test
    fun test3() = runTest {
        calculator.addInput(CalculatorInput.Number(3.0))
        calculator.clearExpression()

        val expression = calculator.state.first().inputs
        assertEquals(0, expression.size)
    }
}
