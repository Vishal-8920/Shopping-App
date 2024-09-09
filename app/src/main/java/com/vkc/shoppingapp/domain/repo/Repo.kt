package com.vkc.shoppingapp.domain.repo

import android.net.Uri
import com.vkc.shoppingapp.common.ResultState
import  com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(userData:UserData) :Flow<ResultState<String>>
    fun signInWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>
    fun getUserById(uid:String) :Flow<ResultState<UserDataParent>>
    fun userProfileImg(uri:Uri) :Flow<ResultState<String>>
    fun updateUserProfile(userDataParent: UserDataParent) :Flow<ResultState<String>>

}