package org.azurestar.kotlinisfun.triviaapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    defaultValue: Int = 0,
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    numberColor: Color = Color.Black,
    style: TextStyle = TextStyle.Default,
    elevation: Dp = 5.dp,
    outlineColor: Color = Color.Transparent,
    increaseIconImageVector: ImageVector = Icons.Default.Add,
    decreaseIconImageVector: ImageVector = Icons.Default.Remove,
    onValueChange: (Int) -> Unit
) {
    if (!checkValues(defaultValue, min, max))
        throw Exception("Invalid Default Value: $defaultValue, Min: $min or/and Max: $max")

    Card(elevation = elevation, border = BorderStroke(1.dp, outlineColor)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { onValueChange(defaultValue + 1) }, enabled = defaultValue < max) {
                Icon(imageVector = increaseIconImageVector, contentDescription = "Increase")
            }
            Text(text = defaultValue.toString(), style = style, color = numberColor)
            IconButton(onClick = { onValueChange(defaultValue - 1) }, enabled = defaultValue > min) {
                Icon(imageVector = decreaseIconImageVector, contentDescription = "Decrease")
            }
        }
    }
}

fun checkValues(value: Int, min: Int, max: Int) = !(min > max || value > max || value < min)