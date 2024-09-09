package com.vkc.shoppingapp.domain.useCase

import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.domain.models.UserDataParent
import com.vkc.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val repo: Repo) {

    fun getUserById(uid:String) : Flow<ResultState<UserDataParent>>{
        return repo.getUserById(uid)
    }
}