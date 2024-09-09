package com.vkc.shoppingapp.presentation.navigation

import kotlinx.serialization.Serializable

/*@Serializable
sealed class AuthScreen{
    @Serializable
    object LoginScreen : AuthScreen()

    @Serializable
    object SignUpScreen : AuthScreen()
}

@Serializable
sealed class MainScreen{



    @Serializable
    object ProfileScreen : MainScreen()

    @Serializable
    object HomeScreen : MainScreen()

    @Serializable
    object WishListScreen : MainScreen()

    @Serializable
    object CartScreen : MainScreen()

    @Serializable
    object ProductDetailScreen : MainScreen()

    @Serializable
    object CheckOutScreen : MainScreen()

}*/



sealed class SubNavigation{

    @Serializable
    data object AuthScreen : SubNavigation()
    @Serializable
    data object MainScreen : SubNavigation()
}



sealed class Routes {

    @Serializable
    data object LoginScreen : Routes()

    @Serializable
    data object SignUpScreen : Routes()

    @Serializable
    object ProfileScreen : Routes()

    @Serializable
    object HomeScreen : Routes()

    @Serializable
    object WishListScreen : Routes()

    @Serializable
    object CartScreen : Routes()

    @Serializable
    object ProductDetailScreen : Routes()

    @Serializable
    object CheckOutScreen : Routes()
}
