package com.vkc.shoppingapp.data.repo

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.vkc.shoppingapp.common.ResultState
import com.vkc.shoppingapp.common.USER_COLLECTION
import com.vkc.shoppingapp.domain.models.UserData
import com.vkc.shoppingapp.domain.models.UserDataParent
import com.vkc.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var firebaseFirestore: FirebaseFirestore):Repo {

    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseAuth.createUserWithEmailAndPassword(userData.email,userData.password).addOnCompleteListener { it ->

            if (it.isSuccessful){

                firebaseFirestore.collection(USER_COLLECTION)
                    .document(it.result.user?.uid.toString()).set(userData)
                    .addOnCompleteListener {

                        if (it.isSuccessful){
                            trySend(ResultState.Success("User Register Successfully and add to firestore"))
                        }else{
                            if (it.exception != null) {
                                trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                            }
                        }
                    }

                trySend(ResultState.Success("Account Created"))
            }else{

                if (it.exception != null){

                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun signInWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>  = callbackFlow {
        trySend(ResultState.Loading)

        firebaseAuth.signInWithEmailAndPassword(userData.email,userData.password).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("Log In successfully"))
            }else{

                if (it.exception != null ){
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun getUserById(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION)
            .document(uid).get().addOnCompleteListener {
                if (it.isSuccessful){
                    val data = it.result.toObject(UserData::class.java)!!
                    val userDataParent = UserDataParent(it.result.id,data)
                    trySend(ResultState.Success(userDataParent))
                }else{
                    if (it.exception != null){
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            }
        awaitClose {
            close()
        }
    }

    override fun userProfileImg(uri: Uri): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
         FirebaseStorage.getInstance().reference.child("UserProfileImage/${System.currentTimeMillis()}+${firebaseAuth.currentUser?.uid}")
             .putFile(uri).addOnCompleteListener {
                it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                   trySend(ResultState.Success(imageUrl.toString()))

                }
                 if (it.exception != null){
                     trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                 }
             }
            awaitClose { close() }
    }

    override fun updateUserProfile(userDataParent: UserDataParent): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
            // .update(userDataParent.userData.toMap())
            .set(userDataParent.userData).addOnCompleteListener {
                if (it.isSuccessful){
                    trySend(ResultState.Success("User Data Updated Successfully"))
                }else{
                    if (it.exception != null){
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            }
         awaitClose { close() }

    }


}