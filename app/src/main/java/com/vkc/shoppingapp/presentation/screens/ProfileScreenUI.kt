package com.vkc.shoppingapp.presentation.screens

import android.net.Uri
import  com.vkc.shoppingapp.domain.models.UserData
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.firebase.auth.FirebaseAuth
import com.vkc.shoppingapp.domain.models.UserDataParent
import com.vkc.shoppingapp.presentation.navigation.Routes
import com.vkc.shoppingapp.presentation.screens.Utils.CustomTextField
import com.vkc.shoppingapp.presentation.screens.Utils.LogoutAlertDialog
import com.vkc.shoppingapp.presentation.viewModel.ShoppingAppViewModel
import com.vkc.shoppingapp.ui.theme.AppColor

@Composable
fun ProfileScreenUi(

    firebaseAuth: FirebaseAuth,
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)
    }
    // these are all State
    val state = viewModel.profileScreenState.collectAsStateWithLifecycle()
    val userProfileImgState = viewModel.userProfileImgState.collectAsState()
    val updateUserProfileState = viewModel.updateUserProfileState.collectAsState()

    // these are variable
    val showLogOutDialog = remember { mutableStateOf(false) }
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val imageUrl = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }

    val context = LocalContext.current


    // Auto Fill Profile Details from database
    val firstName = remember {
        mutableStateOf(
            viewModel.profileScreenState.value.userData?.userData?.firstName ?: ""
        )
    }
    val lastName = remember {
        mutableStateOf(
            viewModel.profileScreenState.value.userData?.userData?.lastName ?: ""
        )
    }
    val email = remember {
        mutableStateOf(
            viewModel.profileScreenState.value.userData?.userData?.email ?: ""
        )
    }
    val phone = remember {
        mutableStateOf(
            viewModel.profileScreenState.value.userData?.userData?.phone ?: ""
        )
    }



    LaunchedEffect(state.value.userData) {
        state.value.userData?.userData?.let { userData ->
            firstName.value = userData.firstName
            lastName.value = userData.lastName
            email.value = userData.email
            phone.value = userData.phone
            imageUrl.value = userData.profileImg

        }

    }
    //  taking img form the user

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->

        if (uri != null) {
            viewModel.userProfileImg(uri)
            imageUri.value = uri
        }

    }
    // update profile
    if (userProfileImgState.value.userData != null) {
        imageUrl.value = userProfileImgState.value.userData.toString()
    } else if (userProfileImgState.value.errorMessage != null) {
        Toast.makeText(context, userProfileImgState.value.errorMessage, Toast.LENGTH_SHORT).show()
    } else if (userProfileImgState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AppColor)
        }

    }
    // update user details
    if (updateUserProfileState.value.userData != null) {
        Toast.makeText(context, updateUserProfileState.value.userData, Toast.LENGTH_SHORT).show()
    } else if (updateUserProfileState.value.errorMessage != null) {
        Toast.makeText(context, updateUserProfileState.value.errorMessage, Toast.LENGTH_SHORT)
            .show()
    } else if (updateUserProfileState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = AppColor)
        }
    }



    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = AppColor)
        }
    } else if (state.value.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.value.errorMessage.toString())
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                // Sub composeAsyncImage function is use to show img through url with the help of coil
                SubcomposeAsyncImage(
                    model = if (isEditing.value) imageUri.value else imageUrl.value,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, color = AppColor, CircleShape)
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator(color = AppColor)
                        is AsyncImagePainter.State.Error -> Icon(Icons.Default.Person, contentDescription = null)
                        else -> SubcomposeAsyncImageContent()
                    }
                }
                if (isEditing.value) {
                    IconButton(
                        onClick = {
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.BottomEnd)
                            .background(AppColor, CircleShape)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Change Picture",
                            tint = Color.White,)
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),

                ) {


                CustomTextField(
                    value = firstName.value, onValueChange = { firstName.value = it },
                    label = "firstName",
                    placeholder = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    readOnly = if (isEditing.value) false else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = AppColor,
                        focusedBorderColor = AppColor,
                        focusedLabelColor = AppColor,
                        cursorColor = AppColor
                    ),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .weight(1f)
                    //   .height(50.dp),
                )

                CustomTextField(
                    value = lastName.value, onValueChange = { lastName.value = it },
                    label = "lastName",
                    placeholder = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    readOnly = if (isEditing.value) false else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = AppColor,
                        focusedBorderColor = AppColor,
                        focusedLabelColor = AppColor,
                        cursorColor = AppColor
                    ), shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                    //  .height(50.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = email.value,
                onValueChange = {},
                label = "email",
                placeholder = "",
                readOnly = if (isEditing.value) false else true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColor,
                    focusedBorderColor = AppColor,
                    focusedLabelColor = AppColor,
                    cursorColor = AppColor
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = phone.value,
                onValueChange = {},
                label = "phone",
                placeholder = "",
                readOnly = if (isEditing.value) false else true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColor,
                    focusedBorderColor = AppColor,
                    focusedLabelColor = AppColor,
                    cursorColor = AppColor,


                ),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    showLogOutDialog.value = true
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(AppColor),
                shape = RoundedCornerShape(10.dp),

                ) {
                Text(text = "Log Out", color = Color.White)
            }
            // condition for Log out

            if (showLogOutDialog.value) {
                LogoutAlertDialog(
                    onDismiss = {
                        showLogOutDialog.value = false
                    },
                    onConform = {
                        firebaseAuth.signOut()
                        navController.navigate(Routes.LoginScreen)
                    }
                )
            }



            Spacer(modifier = Modifier.height(20.dp))

            if (isEditing.value == false) {

                OutlinedIconButton(
                    onClick = { isEditing.value = !isEditing.value },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 2.dp, color = AppColor)
                ) {
                    Text(text = "Edit Profile")
                }
            } else {

                OutlinedIconButton(
                    onClick = {
                        val updateUserData = UserData(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            phone = phone.value,
                            email = email.value,
                            profileImg = imageUrl.value
                        )
                        val userDataParent = UserDataParent(
                            nodeId = state.value.userData!!.nodeId,
                            userData = updateUserData
                        )
                        viewModel.updateUserProfile(userDataParent)
                        isEditing.value = !isEditing.value
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 2.dp, color = AppColor)
                ) {
                    Text(text = "Save Profile")
                }
            }
        }
    }
}