package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalcView()
                }
            }
        }
    }
}


@Preview
@Composable
fun CalcView(){
    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }
    var displayText by rememberSaveable { mutableStateOf("0") }

    if (complete && operation != "") {
        var answer by rememberSaveable { mutableStateOf(0) }
        when (operation){
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> answer = leftNumber / rightNumber
        }
        displayText = answer.toString()
    } else if (operation != "" && !complete) {
        displayText = rightNumber.toString()
    }
    else {
        displayText = leftNumber.toString()
    }

    fun numberPress(btnNum: Int){
        if(complete){
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
        }
        if (operation != "" && !complete){
            rightNumber = (rightNumber * 10) + btnNum
        }
        if (operation == "" && !complete){
            leftNumber = (leftNumber * 10) + btnNum
        }
    }

    fun operationPress(op: String){
        if (!complete){
            operation = op
        }
    }

    fun equalsPress(){
        complete = true
    }

    Column (modifier = Modifier.background(Color.LightGray)
        ) {
        CalcDisplay(display = displayText)
        Row {
            Column {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(display = displayText, startNum = i, numButtons = 3)
                }
                Row {
                    CalcNumericButton(number = 0, display = displayText)
                    CalcEqualsButton(display = displayText)
                }
            }
            Column {
                CalcOperationButton(operation = "+", display = displayText)
                CalcOperationButton(operation = "-", display = displayText)
                CalcOperationButton(operation = "*", display = displayText)
                CalcOperationButton(operation = "/", display = displayText)
            }
        }
    }

}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int){
    val endNum = startNum + numButtons
    Row(modifier = Modifier.padding(0.dp)) {
        for (i in startNum until endNum){
            CalcNumericButton(number = i, display = display)
        }
    }

}

@Composable
fun CalcDisplay(display: MutableState<String>){
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(5.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>){
    Button(
        onClick = {
            display.value += number.toString()
    },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>){
    Button(
        onClick = {
        //empty onClick
    },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>){
    Button(
        onClick = {
            display.value = "0"
    },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = "=")
    }
}
