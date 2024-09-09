package com.vkc.shoppingapp.domain.useCase

import android.net.Uri
import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImgUseCase @Inject constructor(private val repo: Repo) {

    fun userProfileImg(uri: Uri) : Flow<ResultState<String>>{
        return repo.userProfileImg(uri)
    }
}