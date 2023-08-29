package com.mayka.talamyd.di

import com.mayka.talamyd.auth.data.AuthService
import com.mayka.talamyd.auth.domain.repository.AuthRepository
import com.mayka.talamyd.auth.domain.repository.AuthRepositoryImpl
import com.mayka.talamyd.auth.domain.usecase.SignInUseCase
import com.mayka.talamyd.auth.domain.usecase.SignUpUseCase
import com.mayka.talamyd.common.util.provideDispatcher
import com.mayka.talamyd.utils.SharedSettingsHelper
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }

}

private val coreModule = module {
    single {
        SharedSettingsHelper(
            get(named(SharedSettingsHelper.encryptedSettingsName))
        )
    }
}

inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}


private val utilityModule = module {
    factory { provideDispatcher() }
}
expect val platformModule: Module
fun getSharedModules() = listOf(authModule, utilityModule, platformModule, coreModule)
