package com.aryanraj.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aryanraj.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val viewModel : ViewModelTemperatures = viewModel()
     MainScreen(
         isFahrenheit = viewModel.isFahrenheit,
         result = viewModel.convertedTemperature,
         convertTemp = { viewModel.calculateConversion(it)},
         toggleSwitch = { viewModel.doSwitchToggle()}
     )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    toggleSwitch: () -> Unit) {

    var inputTextState by remember { mutableStateOf("") }

    fun onTextChange(newValue : String){
        inputTextState = newValue
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize())
    {
        val switchSize = remember { 150.dp }
        Switch(checked = isFahrenheit, onCheckedChange = {toggleSwitch()}, Modifier.size(switchSize))
        
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(Color.White)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = inputTextState,
                    onValueChange = { onTextChange(it) },
                    label = { Text(text = "Enter temperature") },
                    modifier = Modifier.padding(16.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        Text(text = if (isFahrenheit) "\u2109" else "\u2103")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { convertTemp(inputTextState) }) {
                    if (isFahrenheit) {
                        Text(text = "Convert to Celsius")
                    } else {
                        Text(text = "Convert to Fahrenheit")
                    }
                }
            }//end column
        }//end card 
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .height(60.dp)
                .width(150.dp)
        ) 
        {
    Text(text = "Result: $result",
        Modifier.padding(16.dp)) 
        }
        
        Spacer(modifier = Modifier.height(40.dp))

            if (result > 0.toString()) {

                Image(
                    painter = painterResource(id = R.drawable.hot_removebg_preview),
                    contentDescription = null
                )
            } else if (result <= 0.toString()) {

                Image(
                    painter = painterResource(id = R.drawable.cold_removebg_preview),
                    contentDescription = null
                )
            }  else {
                Image(painter = painterResource(id = R.drawable.opps),
                    contentDescription = null)
            }
        }
    } 


