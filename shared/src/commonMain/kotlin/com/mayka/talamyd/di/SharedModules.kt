package com.mayka.talamyd.di

import com.mayka.talamyd.auth.di.authModule
import com.mayka.talamyd.common.util.provideDispatcher
import com.mayka.talamyd.home.di.courseModule
import com.mayka.talamyd.utils.SharedSettingsHelper
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

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

fun getSharedModules() = listOf(authModule, utilityModule, platformModule, coreModule, courseModule)