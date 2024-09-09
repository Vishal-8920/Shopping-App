package com.vkc.shoppingapp.presentation.screens

import SuccessAlertDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vkc.shoppingapp.R
import com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.presentation.navigation.Routes


import com.vkc.shoppingapp.presentation.screens.Utils.CustomTextField
import com.vkc.shoppingapp.presentation.viewModel.ShoppingAppViewModel
import com.vkc.shoppingapp.ui.theme.AppBg
import com.vkc.shoppingapp.ui.theme.AppColor

@Composable
fun LoginScreenUi(viewModel: ShoppingAppViewModel = hiltViewModel(), navController: NavController) {


    val context = LocalContext.current
    val state = viewModel.signInScreenState.collectAsState()

    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = AppColor)
        }
    } else if (state.value.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.value.error.toString())
        }
    } else if (state.value.signInUserData != null) {
            navController.navigate(Routes.HomeScreen)
    } else {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val isPasswordHidden = remember {
            mutableStateOf(true)
        }


        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {


                Text(text = "Login",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start))


            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = email.value, onValueChange = { email.value = it },
                label = "Email",
                placeholder = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                leadingIcon = Icons.Default.Email,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColor,
                    focusedBorderColor = AppColor,
                    focusedLabelColor = AppColor,
                    cursorColor = AppColor
                )
            )
            Spacer(modifier = Modifier.height(7.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = {
                    Text(text = "Confirm Password")
                },
                placeholder = {
                    Text(text = "Confirm Password")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription =null )
                }, trailingIcon = {
                    val image = if (isPasswordHidden.value){
                        Icons.Default.VisibilityOff
                    }else Icons.Default.Visibility

                    IconButton(onClick = { isPasswordHidden.value = !isPasswordHidden.value}) {
                        Icon(imageVector = image, contentDescription = null)
                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColor,
                    focusedBorderColor = AppColor,
                    focusedLabelColor = AppColor,
                    cursorColor = AppColor
                ),
                visualTransformation = if (isPasswordHidden.value){

                    PasswordVisualTransformation()
                }else{
                    VisualTransformation.None

                }

            )

            Text(text = "Forget Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {

                    })

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.value.isNotBlank() && password.value.isNotBlank()
                    ) {
                        val userData = UserData(
                            firstName = "",
                            lastName = "",
                            email = email.value,
                            password = password.value,
                            phone = ""

                        )
                        viewModel.logInUser(userData)
                    } else {
                        Toast.makeText(context, "Please fill All Detail", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(AppColor),
                shape = RoundedCornerShape(10.dp),
                // border = BorderStroke(width = 2.dp, color = Color.Magenta)
            ) {
                Text(text = "Login", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier, horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "don't have an account?")
                Text(
                    text = "SignUp", modifier = Modifier
                        .padding(3.dp)
                        .clickable {
                            navController.navigate(Routes.SignUpScreen)
                        }, color = AppColor
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = AppBg,
                    modifier = Modifier.width(150.dp)
                )
                Text(text = "OR", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = AppBg,
                    modifier = Modifier.width(150.dp)
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(),
                //  .background(AppBg),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(width = 2.dp, color = AppColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 1.dp),
                    alignment = Alignment.CenterStart
                )
                Text(text = "Log in with Google", modifier = Modifier.offset(15.dp), color = AppColor)

            }
        }
    }


}