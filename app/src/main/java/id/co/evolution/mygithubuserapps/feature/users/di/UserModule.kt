package id.co.evolution.mygithubuserapps.feature.users.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.evolution.mygithubuserapps.feature.users.data.repository.UserRepositoryImpl
import id.co.evolution.mygithubuserapps.feature.users.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}