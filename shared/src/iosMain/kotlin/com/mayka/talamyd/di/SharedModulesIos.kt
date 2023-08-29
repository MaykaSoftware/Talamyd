package com.mayka.talamyd.di

import com.mayka.talamyd.utils.SharedSettingsHelper
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalSettingsImplementation
actual val platformModule: Module = module {
    single<Settings>(named(SharedSettingsHelper.unencryptedSettingsName)) {
        NSUserDefaultsSettings.Factory().create(SharedSettingsHelper.DATABASE_NAME)
    }
    single<Settings>(named(SharedSettingsHelper.encryptedSettingsName)) {
        KeychainSettings(service = SharedSettingsHelper.ENCRYPTED_DATABASE_NAME)
    }
}