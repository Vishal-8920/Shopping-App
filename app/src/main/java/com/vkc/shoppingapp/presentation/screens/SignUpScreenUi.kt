package com.vkc.shoppingapp.presentation.screens


import SuccessAlertDialog
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vkc.shoppingapp.R

import com.vkc.shoppingapp.domain.models.UserData
//import com.vkc.shoppingapp.presentation.navigation.AuthScreen
import com.vkc.shoppingapp.presentation.navigation.Routes
import com.vkc.shoppingapp.presentation.navigation.SubNavigation
import com.vkc.shoppingapp.presentation.screens.Utils.CustomTextField
import com.vkc.shoppingapp.presentation.viewModel.ShoppingAppViewModel
import com.vkc.shoppingapp.ui.theme.AppBg
import com.vkc.shoppingapp.ui.theme.AppColor


@Composable
fun SignUpScreenUi(viewModel: ShoppingAppViewModel = hiltViewModel(),navController: NavController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }

    val isPasswordHidden = remember {
        mutableStateOf(true)
    }


    val state = viewModel.signUpScreenState.collectAsState()
    val context = LocalContext.current



    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = AppColor)
        }
    } else if (state.value.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.value.errorMessage.toString())
        }
    } else if (state.value.userData != null) {
          SuccessAlertDialog (
              onClick = {
                  navController.navigate(Routes.LoginScreen)
              }
          )
    }else






            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Signup",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start))



                Spacer(modifier = Modifier.height(7.dp))



                Row(

                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    // horizontalArrangement = Arrangement.SpaceBetween,
                    //verticalAlignment = Alignment.CenterVertically
                ) {


                    CustomTextField(
                        value = firstName.value, onValueChange = { firstName.value = it },
                        label = "FirstName",
                        placeholder = "First Name",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = Icons.Default.Person,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = AppColor,
                            focusedBorderColor = AppColor,
                            focusedLabelColor = AppColor,
                            cursorColor = AppColor
                        ),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier
                            .weight(1f)
                        //    .height(50.dp),
                    )

                    CustomTextField(
                        value = lastName.value, onValueChange = { lastName.value = it },
                        label = "LastName",
                        placeholder = "Last Name",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = Icons.Default.Person,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = AppColor,
                            focusedBorderColor = AppColor,
                            focusedLabelColor = AppColor,
                            cursorColor = AppColor
                        ),shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                          //  .height(50.dp),
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
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

                CustomTextField(
                    value = phone.value, onValueChange = { phone.value = it },
                    label = "Number",
                    placeholder = "Phone no",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    leadingIcon = Icons.Default.Phone,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = AppColor,
                        focusedBorderColor = AppColor,
                        focusedLabelColor = AppColor,
                        cursorColor = AppColor
                    )
                )
                Spacer(modifier = Modifier.height(7.dp))

                CustomTextField(
                    value = password.value, onValueChange = { password.value = it },
                    label = "Password",
                    placeholder = "Enter Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    leadingIcon = Icons.Default.Lock,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
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
                            Icons.Default.Visibility
                        }else Icons.Default.VisibilityOff

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

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && email.value.isNotBlank() && confirmPassword.value.isNotBlank() &&
                            password.value.isNotBlank() && phone.value.isNotBlank()
                        ) {

                            if (password.value == confirmPassword.value) {

                                val userData = UserData(
                                    firstName = firstName.value,
                                    lastName = lastName.value,
                                    email = email.value,
                                    password = password.value,
                                    phone = phone.value
                                )
                                viewModel.createUser(userData)
                                Toast.makeText(context, "Signup Successfully", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    context,
                                    " Password do not match",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        } else {
                            Toast.makeText(context, "Please fill All Detail", Toast.LENGTH_SHORT)
                                .show()
                        }




                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                      //  .padding(start = 50.dp, end = 50.dp),
                    colors = ButtonDefaults.buttonColors(AppColor),
                    shape = RoundedCornerShape(10.dp),
                   // border = BorderStroke(width = 2.dp, color = Color.Magenta)
                ) {
                    Text(text = "Sign Up", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier, horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have an account?")
                    Text(
                        text = "Login", modifier = Modifier
                            .padding(3.dp)
                            .clickable {
                                navController.navigate(Routes.LoginScreen)
                            }, color = AppColor
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(thickness = 1.dp, color = AppBg, modifier = Modifier.width(150.dp))
                    Text(text = "OR", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                    HorizontalDivider(thickness = 1.dp, color = AppBg, modifier = Modifier.width(150.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Sign up with Google", color = AppColor)
                }



            }
        }


