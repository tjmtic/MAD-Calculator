package com.example.snapapp.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.Calculator
import com.example.calculator.CalculatorState
import com.example.calculator.CalculatorUiState
import com.example.calculator.models.CalculatorInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(private val calculator: Calculator) : ViewModel() {
    //Unidirectional Data Flow Pattern - States are Passed Up (to viewModel), Events Passed Down (from viewModel)//

    //STATES//
    //Repositories with background updates can use this pattern
    //to create implicit data pipes to the viewModel
    val calculatorState = calculator.state.stateIn( //Hot Flow State Updates for the viewModel are scoped to the viewModel
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CalculatorState()
    )

    //Sample Multi Repository state transformation (not actually necessary here of course)
    val uiState : StateFlow<ViewModelUiState> = combine(calculatorState, calculatorState){ state1, state2 ->
        //ViewModel will display loading if some dependency is loading
        val loading = state1.state is CalculatorUiState.Loading || state2.state is CalculatorUiState.Loading

        //Shared Error state
        val error = when {
            state1.state is CalculatorUiState.Error -> (state1.state as CalculatorUiState.Error).message
            state2.state is CalculatorUiState.Error -> (state2.state as CalculatorUiState.Error).message
            else -> null
        }

        //resultant viewmodel state
        ViewModelUiState(loading, error)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ViewModelUiState()
    )

    //EVENTS//
    fun addInput(input: CalculatorInput) {
        println("Adding input ${input}")
        calculator.addInput(input)
    }

    fun clearExpression() {
        println("Clearing input")
        calculator.clearExpression()
    }

    fun calculate(){
        println("Calculating")
        viewModelScope.launch{ //ViewModel Operations are be Scoped to ViewModel lifecycle, by best practices
            calculator.calculate()
        }
    }

}

data class ViewModelUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
