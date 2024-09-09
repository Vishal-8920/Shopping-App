package com.vkc.shoppingapp.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vkc.shoppingapp.data.repo.RepoImpl
import com.vkc.shoppingapp.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideRepo(firebaseAuth: FirebaseAuth,firebaseFirestore: FirebaseFirestore):Repo{
      return RepoImpl(firebaseAuth,firebaseFirestore)
    }
}