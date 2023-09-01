package com.mayka.talamyd.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mayka.talamyd.utils.SharedSettingsHelper
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Settings>(named(SharedSettingsHelper.encryptedSettingsName)) {
        SharedPreferencesSettings(
            EncryptedSharedPreferences.create(
                get(),
                SharedSettingsHelper.ENCRYPTED_DATABASE_NAME,
                MasterKey.Builder(get())
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ), false
        )
    }
    single<Settings>(named(SharedSettingsHelper.unencryptedSettingsName)) {
        SharedPreferencesSettings.Factory(get()).create(SharedSettingsHelper.DATABASE_NAME)
    }
}