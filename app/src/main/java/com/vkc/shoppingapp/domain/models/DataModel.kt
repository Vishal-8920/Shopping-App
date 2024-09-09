package com.vkc.shoppingapp.domain.models

data class UserData (
    val firstName: String = "",
    val lastName : String = "",
    val email :String = "",
    val password:String = "",
    val phone: String = "",
    val profileImg :String = ""

)







data class UserDataParent(val nodeId :String = "",val userData: UserData = UserData())