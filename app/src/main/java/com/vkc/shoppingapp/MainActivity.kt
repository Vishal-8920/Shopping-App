package com.vkc.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.vkc.shoppingapp.presentation.navigation.App
import com.vkc.shoppingapp.presentation.screens.HomeScreenUi
import com.vkc.shoppingapp.presentation.screens.LoginScreenUi
import com.vkc.shoppingapp.presentation.screens.SignUpScreenUi
import com.vkc.shoppingapp.ui.theme.ShoppingAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
     @Inject
     lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   App(modifier = Modifier.padding(innerPadding), firebaseAuth = firebaseAuth )
                   // HomeScreenUi(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}