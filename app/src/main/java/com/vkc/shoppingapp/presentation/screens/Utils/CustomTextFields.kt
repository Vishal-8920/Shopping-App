package com.vkc.shoppingapp.presentation.screens.Utils

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label,
            style = TextStyle(fontSize = 14.sp)) },
        placeholder = { Text(placeholder,
            style = TextStyle(fontSize = 14.sp)
        )},
        readOnly = readOnly,
        modifier = modifier,
        singleLine = singleLine,
        leadingIcon = leadingIcon?.let { icon ->
            { Icon(imageVector = icon, contentDescription = null) }
        },
        trailingIcon =trailingIcon?.let { icon ->
            { Icon(imageVector = icon, contentDescription = null) }
        } ,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        shape = shape,
        colors = colors
    )
}