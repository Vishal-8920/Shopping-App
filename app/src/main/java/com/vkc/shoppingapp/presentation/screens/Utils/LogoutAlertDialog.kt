package com.vkc.shoppingapp.presentation.screens.Utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vkc.shoppingapp.ui.theme.AppColor

@Preview(showBackground = true)
@Composable
fun LogoutAlertDialog(
    onDismiss: () -> Unit = {},
    onConform: () -> Unit = {}
) {

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
               // .fillMaxSize()
                .height(300.dp)
                .padding(16.dp),
            RoundedCornerShape(16.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile Img",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Log Out", fontWeight = FontWeight.Bold, color = AppColor)

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Do you really \n Want To Logout", textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(onClick = onConform,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        border = BorderStroke(width = 1.dp, color = AppColor)
                        ) {

                        Text(text = "Cancel", color = AppColor)

                    }
                    Button(onClick = onConform,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                        ,
                        colors = ButtonDefaults.buttonColors(AppColor)) {

                        Text(text = "Log Out", color = Color.White)

                    }
                }
            }
        }

    }

}