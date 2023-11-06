package com.aryanraj.temperatureconverter

import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.Exception

class ViewModelTemperatures: ViewModel() {
    var isFahrenheit by mutableStateOf(true)

    var convertedTemperature by mutableStateOf("")

    fun doSwitchToggle(){
        isFahrenheit = !isFahrenheit
    }

    fun calculateConversion(inputValue: String){

        try {

            val temperature = inputValue.toDouble()

            if (isFahrenheit){
                convertedTemperature= "%.2f".format((temperature - 32)*5 / 9)
                convertedTemperature += "\u2103"
            }else {
                convertedTemperature = "%.2f".format(temperature * 9 / 5 + 32)
                convertedTemperature += "\u2109"
            }
        }
        catch (e: Exception){
            convertedTemperature = "Invalid input"
        }
    }
}