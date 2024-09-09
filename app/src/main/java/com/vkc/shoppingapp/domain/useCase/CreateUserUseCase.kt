package com.vkc.shoppingapp.domain.useCase

import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repo: Repo) {

    fun createUser(userData: UserData) :Flow<ResultState<String>>{
        return repo.registerUserWithEmailAndPassword(userData)
    }




}