package com.vkc.shoppingapp.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.domain.models.UserDataParent
import com.vkc.shoppingapp.domain.useCase.CreateUserUseCase
import com.vkc.shoppingapp.domain.useCase.GetUserUseCase
import com.vkc.shoppingapp.domain.useCase.LoginUserUseCase
import com.vkc.shoppingapp.domain.useCase.UpdateUserProfileUseCase
import com.vkc.shoppingapp.domain.useCase.UserProfileImgUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val signInUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userProfileImgUseCase: UserProfileImgUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
    ) :ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _signInScreenState = MutableStateFlow(SignInScreenState())
    val signInScreenState =_signInScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _userProfileImgState = MutableStateFlow(UserProfileImgState())
    val userProfileImgState = _userProfileImgState.asStateFlow()
    private val _updateUserProfileState =MutableStateFlow(UpdateUserProfileState())
    val  updateUserProfileState = _updateUserProfileState



    fun createUser(userData: UserData){
        viewModelScope.launch {
            // here i am using collectLatest or sir is used collect
            createUserUseCase.createUser(userData).collectLatest {
                when(it){
                    is ResultState.Error -> {
                      _signUpScreenState.value = SignUpScreenState(errorMessage = it.message)
                    }
                    ResultState.Loading -> {
                        _signUpScreenState.value = SignUpScreenState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _signUpScreenState.value = SignUpScreenState(userData = it.data)
                    }
                }
            }
        }
    }

    fun logInUser(userData: UserData){
        viewModelScope.launch {
            signInUseCase.signInUser(userData).collectLatest {
                when(it){
                    is ResultState.Error ->{
                        _signInScreenState.value =SignInScreenState(error = it.message)
                    }
                    ResultState.Loading -> {
                        _signInScreenState.value =SignInScreenState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _signInScreenState.value =SignInScreenState(signInUserData = it.data)
                    }
                }
            }
        }

    }

    fun getUserById(uid:String){
        viewModelScope.launch {
            getUserUseCase.getUserById(uid).collect {
                when(it){
                    is ResultState.Error -> {
                        _profileScreenState.value = ProfileScreenState(errorMessage = it.message)
                    }
                    ResultState.Loading -> {
                        _profileScreenState.value = ProfileScreenState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _profileScreenState.value = ProfileScreenState(userData = it.data)
                    }
                }
            }
        }
    }

    fun userProfileImg(uri: Uri){
        viewModelScope.launch {
            userProfileImgUseCase.userProfileImg(uri).collect{
                when(it){
                    is ResultState.Error -> {
                        _userProfileImgState.value = UserProfileImgState(errorMessage = it.message)
                    }
                    ResultState.Loading -> {
                        _userProfileImgState.value = UserProfileImgState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _userProfileImgState.value = UserProfileImgState(userData = it.data)
                    }
                }
            }
        }
    }

    fun updateUserProfile(userDataParent: UserDataParent){
        viewModelScope.launch {
            updateUserProfileUseCase.updateUserProfile(userDataParent).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _updateUserProfileState.value = UpdateUserProfileState(errorMessage = it.message)
                    }
                    ResultState.Loading -> {
                        _updateUserProfileState.value = UpdateUserProfileState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _updateUserProfileState.value =UpdateUserProfileState(userData = it.data)
                    }
                }
            }
        }
    }


}






data class SignUpScreenState(
    val isLoading:Boolean = false,
    val errorMessage:String? =null,
    val userData: String? = null
)

data class SignInScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val signInUserData: String? = null
)

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null
)

data class UserProfileImgState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UpdateUserProfileState(
    val isLoading: Boolean = false,
    val errorMessage: String? =null,
    val userData: String? =null
)