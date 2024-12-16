package com.example.snapapp.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculator.models.CalculatorInput
import com.example.calculator.models.Subtract
import com.example.snapapp.calculator.CalculatorConstants.EXTRAS
import com.example.snapapp.calculator.CalculatorConstants.INPUTS
import com.example.snapapp.calculator.CalculatorConstants.OPERATIONS
import com.example.snapapp.ui.theme.MyApplicationTheme

data class CalculatorScreenState(
    val inputs: List<CalculatorInput.Input>,
    val operations: List<CalculatorInput.Operator>,
    val extras: List<String>,
    val expression: String = "",
    val lastResult: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@Composable
fun CalculatorRoute(viewModel: CalculatorViewModel = hiltViewModel()) {
    //Route-level definitions
    val inputs = INPUTS
    val operations = OPERATIONS
    val extras = EXTRAS

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val calculatorState by viewModel.calculatorState.collectAsStateWithLifecycle()

    CalculatorScreen(
        uiState = CalculatorScreenState(
            inputs = inputs,
            operations = operations,
            extras = extras,
            expression = calculatorState.inputs.joinToString(" "){ it.toString() },
            lastResult = calculatorState.result,
            isLoading = uiState.isLoading,
            error = uiState.error
        ),
        onInput = viewModel::addInput,
        onCalculate = viewModel::calculate,
        onClear = viewModel::clearExpression,
    )
}

// Main composable displaying the entire calculator UI
@Composable
fun CalculatorScreen(
    uiState: CalculatorScreenState,
    onInput: (CalculatorInput) -> Unit,
    onCalculate: () -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DisplayArea(
            expression = uiState.expression,
            lastResult = uiState.lastResult,
            isLoading = uiState.isLoading,
            error = uiState.error
        )

        //Spacer(modifier = Modifier.height(8.dp))

        //Spacer(modifier = Modifier.height(8.dp))

        Keypad(
            inputs = uiState.inputs,
            operations = uiState.operations,
            extras = uiState.extras,
            onInput = onInput,
            onCalculate = onCalculate,
            onClear = onClear
        )
    }
}


//View Composables
@Composable
fun DisplayArea(
    expression: String,
    lastResult: Double?,
    isLoading: Boolean,
    error: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            if (error != null) {
                Text(
                    text = "Error: $error",
                    style = TextStyle(color = Color.Red, fontSize = 16.sp)
                )
            }
            Text(
                text = expression,
                style = TextStyle(fontSize = 24.sp, color = Color.Black)
            )
            if (lastResult != null) {
                Text(
                    text = "Last: $lastResult",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun Keypad(
    inputs: List<CalculatorInput.Input>,
    operations: List<CalculatorInput.Operator>,
    extras: List<String>,
    onInput: (CalculatorInput) -> Unit,
    onCalculate: () -> Unit,
    onClear: () -> Unit
) {
    val curInput = rememberSaveable {
        mutableStateOf("")
    }
    val numberRegex = Regex("^-?\\d+(\\.\\d+)?$")
    val isValidNumber = rememberSaveable(curInput.value) {
        numberRegex.matches(curInput.value)
    }

    Text(
        text = "Current: ${curInput.value}",
        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(inputs) { input ->
            CalculatorButton(
                text = input.value.toString(),
                modifier = Modifier.padding(8.dp),
                onClick = { //onInput(input)
                    curInput.value = curInput.value.plus(input)
                }
            )
        }

        items(operations) { operation ->
            CalculatorButton(
                text = operation.operation.toString(),
                modifier = Modifier.padding(8.dp),
                onClick = {
                    if(operation.operation is Subtract && curInput.value.isEmpty()){
                        curInput.value = curInput.value.plus(operation.toString())
                    }
                    else {
                        if(isValidNumber) {
                            onInput(CalculatorInput.Number(curInput.value.toDouble()))
                            curInput.value = ""
                            onInput(operation)
                        }
                    }
                }
            )
        }

        items(extras) { extra ->
            CalculatorButton(
                text = extra,
                modifier = Modifier.padding(8.dp),
                onClick = {
                    if(extra == " " && isValidNumber) {
                        onInput(CalculatorInput.Number(curInput.value.toDouble()))
                        curInput.value = ""
                    }

                    else curInput.value = curInput.value.plus(extra)
                }
            )
        }

        item {
            CalculatorButton(
                text = "Delete",
                modifier = Modifier.padding(8.dp),
                onClick = {
                    if(curInput.value.isNotEmpty()) curInput.value = curInput.value.removeRange(curInput.value.lastIndex, curInput.value.lastIndex+1)

                    println("Deleting ${curInput.value}")
                }
            )
        }

        item {
            CalculatorButton(
                text = "Enter",
                modifier = Modifier.padding(8.dp),
                onClick = { curInput.value = ""; onCalculate() }
            )
        }

        item {
            CalculatorButton(
                text = "Clear",
                modifier = Modifier.padding(8.dp),
                onClick = { curInput.value = ""; onClear() }
            )
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(64.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(contentAlignment = Alignment.Center) {
            BasicText(
                text = text,
                style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCalculatorScreen(){
    MyApplicationTheme {
        CalculatorScreen(CalculatorScreenState(INPUTS, OPERATIONS, EXTRAS,"1 2 +",
            0.0,
            false,
            null),
            {},{},{})
    }
}

@Preview
@Composable
fun PreviewDisplayArea(){
    MyApplicationTheme {
        DisplayArea("1 2 +", 0.0, false, null)
    }
}

@Preview
@Composable
fun PreviewDisplayAreaLoading(){
    MyApplicationTheme {
        DisplayArea("1 2 +", 0.0, true, null)
    }
}

@Preview
@Composable
fun PreviewDisplayAreaError(){
    MyApplicationTheme {
        DisplayArea("1 2 +", 0.0, false, "Test Error")
    }
}

@Preview
@Composable
fun PreviewKeypad(){
    MyApplicationTheme {
        Keypad(INPUTS, OPERATIONS, EXTRAS, {},{},{})
    }
}

@Preview
@Composable
fun PreviewButton(){
    MyApplicationTheme {
        CalculatorButton ("Test", Modifier, {} )
    }
}
