package com.example.calculator

import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.OperationResult
import com.example.calculator.util.Computations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class CalculatorImpl: Calculator {

    //Local Vars
    private val _currentExpression = MutableStateFlow<List<CalculatorInput>>(emptyList())
    private val _lastComputedResult = MutableStateFlow<Double?>(null)

    private val _uiState = MutableStateFlow<CalculatorUiState>(CalculatorUiState.Idle)

    //Encapsulated State
    override val state = combine(_uiState, _currentExpression, _lastComputedResult){uistate, expression, result ->
        CalculatorState(uistate, expression, result)
    }

    override fun addInput(input: CalculatorInput) {
        //Do not allow expression changes while performing an operation
        if(_uiState.value != CalculatorUiState.Loading) {
            //Update state from Success or Error states, since changes have been made
            _uiState.value = CalculatorUiState.Idle
            _currentExpression.value += input
            println("Added input $input")
        }
    }

    override fun clearExpression() {
        if(_uiState.value != CalculatorUiState.Loading) {
            _uiState.value = CalculatorUiState.Idle
            _currentExpression.value = emptyList()
        }
    }

    //Repository and other lower-level utility classes may use this
    //pattern to maintain consistent and predictable thread management
    //when invoked by higher-level components
    // i.e.
    // viewModelScope.launch {
    //      repository.repoFun()
    // }
    //
    // class repository {
    //  suspend fun repoFun(){
    //            withContext(Dispatchers.IO) {
    //               functionA()
    //            }
    // }
    //--Context Scoping and State/Result Handling for the unit function --
    override suspend fun calculate() {
        //Since the input value is NOT being passed in, we will reference it's value locally in a thread-safe manner
        val input = _currentExpression.value
        val result: OperationResult? = try {
                //Update UI State appropriately
                _uiState.value = CalculatorUiState.Loading

                //Specific Thread context fpr specific operation type
                //in this case, "heavy calculation" on the Default (computation) threads
                withContext(Dispatchers.Default) {
                    //Perform the work operation
                    Computations.computeRPN(input)
                }
            } finally {}

        //Check result and update the state values, in case of upstream cancellation or errors
        when(result){
            is OperationResult.Success -> {
                _lastComputedResult.value = result.data as Double
                _uiState.value = CalculatorUiState.Success
            }

            is OperationResult.Error -> {
                _lastComputedResult.value = null //TODO: Or keep the previous successful value visible?
                _uiState.value = CalculatorUiState.Error(result.cause.message)
            }
            //Upstream Cancellation Handling
            else -> {
                _lastComputedResult.value = null
                _uiState.value = CalculatorUiState.Error("Unknown Error")
            }
        }
    }
}

data class CalculatorState(
    val state: CalculatorUiState = CalculatorUiState.Idle,
    val inputs: List<CalculatorInput?> = emptyList(),
    val result: Double? = null
)

sealed class CalculatorUiState {
    data object Idle : CalculatorUiState() // No calculation is ongoing
    data object Loading : CalculatorUiState() // Calculation is in progress
    data object Success: CalculatorUiState() // Calculation completed successfully
    data class Error(val message: String?): CalculatorUiState() // Calculation failed
}


