package com.mayka.talamyd.utils


import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SharedSettingsHelper(
    private val encryptedSettings: Settings,
) {
    var token: String
        get() {
            return encryptedSettings[TOKEN_NAME] ?: ""
        }
        set(value) {
            encryptedSettings[TOKEN_NAME] = value
        }

    var refreshToken: String
        get() {
            return encryptedSettings[REFRESH_TOKEN_NAME] ?: ""
        }
        set(value) {
            encryptedSettings[REFRESH_TOKEN_NAME] = value
        }

    companion object {
        const val DATABASE_NAME = "UNENCRYPTED_SETTINGS"
        const val ENCRYPTED_DATABASE_NAME = "ENCRYPTED_SETTINGS"
        const val encryptedSettingsName = "encryptedSettings"
        const val unencryptedSettingsName = "unencryptedSettings"
        const val TOKEN_NAME = "TOKEN"
        const val REFRESH_TOKEN_NAME = "REFRESH_TOKEN"
    }
}

