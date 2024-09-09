package com.vkc.shoppingapp.presentation.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.vkc.shoppingapp.presentation.screens.CartScreenUi
import com.vkc.shoppingapp.presentation.screens.HomeScreenUi
import com.vkc.shoppingapp.presentation.screens.LoginScreenUi
import com.vkc.shoppingapp.presentation.screens.ProfileScreenUi
import com.vkc.shoppingapp.presentation.screens.SignUpScreenUi
import com.vkc.shoppingapp.presentation.screens.WishListScreenUi
import com.vkc.shoppingapp.ui.theme.AppColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(modifier: Modifier, firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    val selected = remember { mutableIntStateOf(0) }



     val navBackStackEntry by navController.currentBackStackEntryAsState()
     val currentDestination = navBackStackEntry?.destination?.route
     val shouldShowBottomBar = remember { mutableStateOf(false) }

     LaunchedEffect(currentDestination) {
         shouldShowBottomBar.value = when (currentDestination) {
             Routes.LoginScreen::class.qualifiedName, Routes.SignUpScreen::class.qualifiedName -> false
             else -> true
         }
     }

    val bottomNavItemList = listOf(
        BottomNavItem("Home", Icons.Default.Home, unSelectedIcon = Icons.Outlined.Home),
        BottomNavItem("WishList", Icons.Default.Favorite, unSelectedIcon = Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, unSelectedIcon = Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, unSelectedIcon = Icons.Outlined.Person),

        )

    val startScreen = if (firebaseAuth.currentUser == null) {
        Routes.LoginScreen
    } else {
      //  Log.d("TEST_CHECK", "App: { Else function is running as well MainScreen}")
       // SubNavigation.MainScreen
        Routes.HomeScreen
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (shouldShowBottomBar.value) {
                    NavigationBar {
                        bottomNavItemList.forEachIndexed { index, bottomNavItem ->
                            NavigationBarItem(
                                alwaysShowLabel = true,
                                colors = NavigationBarItemColors(
                                    selectedIconColor = Color.White,
                                    selectedTextColor = AppColor,
                                    selectedIndicatorColor = AppColor,
                                    disabledTextColor = Color.Black,
                                    disabledIconColor = Color.Black,
                                    unselectedTextColor = Color.Black,
                                    unselectedIconColor = Color.Black
                                ),
                                selected = selected.intValue == index,
                                onClick = { selected.intValue = index },

                                icon = {
                                    Icon(
                                        imageVector =
                                        bottomNavItem.icon, contentDescription = bottomNavItem.lable
                                    )
                                }, label = {
                                    Text(text = bottomNavItem.lable)
                                }


                            )
                        }

                    }

                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (shouldShowBottomBar.value) innerPadding.calculateBottomPadding() else 0.dp)
            ) {

                NavHost(navController = navController, startDestination = startScreen) {

                    composable<Routes.LoginScreen> {
                        LoginScreenUi(navController = navController)
                    }

                    composable<Routes.SignUpScreen> {
                        SignUpScreenUi(navController = navController)
                    }
                    composable<Routes.HomeScreen> {
                        when (selected.intValue) {
                            0 -> HomeScreenUi()
                            1 -> WishListScreenUi()
                            2 -> CartScreenUi()
                            3 -> ProfileScreenUi(firebaseAuth = firebaseAuth, navController = navController)

                        }
                    }
                    /*
                                navigation<SubNavigation.AuthScreen>(startDestination = Routes.LoginScreen) {
                                    composable<Routes.LoginScreen> {
                                        LoginScreenUi(navController = navController)
                                    }

                                    composable<Routes.SignUpScreen> {
                                        SignUpScreenUi(navController = navController)
                                    }
                                }*/

                    /*navigation<SubNavigation.MainScreen>(startDestination = Routes.WishListScreen) {

                        composable<Routes.HomeScreen> {
                            HomeScreenUi()
                        }
                        composable<Routes.ProfileScreen> {
                            ProfileScreenUi()
                        }
                        composable<Routes.CartScreen> {
                            CartScreenUi()
                        }
                        composable<Routes.WishListScreen> {
                            WishListScreenUi()
                        }
                    }*/


                }
            }


        }


    }



    /*  val navController = rememberNavController()
      Box(modifier = Modifier.fillMaxSize()) {
         NavHost(navController = navController, startDestination = Routes.SignUpScreen) {

             composable<Routes.LoginScreen> {
             LoginScreenUi()
             }

             composable<Routes.SignUpScreen> {
              SignUpScreenUi(navController = navController)
             }
         }
      }*/
}

