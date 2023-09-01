package com.mayka.talamyd.auth.di

import com.mayka.talamyd.auth.data.AuthService
import com.mayka.talamyd.auth.domain.AuthRepository
import com.mayka.talamyd.auth.domain.AuthRepositoryImpl
import com.mayka.talamyd.auth.domain.SignInUseCase
import com.mayka.talamyd.auth.domain.SignUpUseCase
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}