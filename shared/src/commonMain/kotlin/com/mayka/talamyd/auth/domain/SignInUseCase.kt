package com.mayka.talamyd.auth.domain

import com.mayka.talamyd.auth.data.AuthResultData
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.utils.SharedSettingsHelper
import com.mayka.talamyd.utils.emailRegex
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInUseCase : KoinComponent {

    private val repository: AuthRepository by inject()
    private val sharedSettingsHelper: SharedSettingsHelper by inject()

    suspend operator fun invoke(
        email: String,
        password: String
    ): MyResult<AuthResultData> {
        if (email.isBlank() || emailRegex(email)) {
            return MyResult.Error(
                message = "Invalid email"
            )
        }
        if (password.isBlank() || password.length < 4) {
            return MyResult.Error(
                message = "Invalid password or too short!"
            )
        }
        val authResult = repository.signIn(email, password)
        sharedSettingsHelper.token = "Bearer ${authResult.data?.token?.accessToken}"
        sharedSettingsHelper.refreshToken = authResult.data?.token?.refreshTokenData?.refreshToken ?: ""

        return authResult
    }
}