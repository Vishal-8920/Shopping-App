package com.vkc.shoppingapp.domain.useCase

import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.domain.models.UserDataParent
import com.vkc.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(private val repo: Repo) {

    fun updateUserProfile(userDataParent: UserDataParent) : Flow<ResultState<String>>{
        return repo.updateUserProfile(userDataParent)
    }

}