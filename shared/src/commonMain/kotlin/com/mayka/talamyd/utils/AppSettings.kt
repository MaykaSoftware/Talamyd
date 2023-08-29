package com.mayka.talamyd.utils


import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class AppSettings(private val settings: FlowSettings) {

    val experimentalFeaturesEnabledFlow = settings
        .getBooleanFlow(EXPERIMENTAL_FEATURES_ENABLED, false)

    suspend fun isExperimentalFeaturesEnabled() =
        settings.getBoolean(EXPERIMENTAL_FEATURES_ENABLED, false)

    suspend fun setExperimentalFeaturesEnabled(value: Boolean) {
        settings.putBoolean(EXPERIMENTAL_FEATURES_ENABLED, value)
    }

    suspend fun getAccessToken(): String {
        return settings.getString(TOKEN_NAME, TOKEN_NOT_SET)
    }

    fun getAccessTokenFlow(): Flow<String> {
        return settings.getStringFlow(TOKEN_NAME, TOKEN_NOT_SET)
    }

    suspend fun setAccessToken(accessToken: String) {
        settings.putString(TOKEN_NAME, accessToken)
    }

    suspend fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN_NAME, REFRESH_TOKEN_NOT_SET)
    }

    fun getRefreshTokenFlow(): Flow<String> {
        return settings.getStringFlow(REFRESH_TOKEN_NAME, REFRESH_TOKEN_NOT_SET)
    }

    suspend fun setRefreshToken(refreshToken: String) {
        settings.putString(REFRESH_TOKEN_NAME, refreshToken)
    }

    private fun getEnabledLanguagesSetFromString(settingsString: String?) =
        settingsString?.split(",")?.toSet() ?: emptySet()

    fun developerModeFlow() =
        settings.getBooleanFlow(DEVELOPER_MODE, false)

    suspend fun setDeveloperMode(b: Boolean) {
        settings.putBoolean(DEVELOPER_MODE, b)
    }

    companion object {
        const val DEVELOPER_MODE = "developer_mode"
        const val EXPERIMENTAL_FEATURES_ENABLED = "experimental_features_enabled"
        const val TOKEN_NOT_SET = ""
        const val TOKEN_NAME = "TOKEN"
        const val REFRESH_TOKEN_NOT_SET = ""
        const val REFRESH_TOKEN_NAME = "REFRESH_TOKEN"
    }
}