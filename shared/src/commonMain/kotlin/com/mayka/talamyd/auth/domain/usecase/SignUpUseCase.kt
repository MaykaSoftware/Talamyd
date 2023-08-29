package com.mayka.talamyd.auth.domain.usecase

import com.mayka.talamyd.auth.domain.model.AuthResultData
import com.mayka.talamyd.auth.domain.repository.AuthRepository
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.utils.SharedSettingsHelper
import com.mayka.talamyd.utils.emailRegex
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpUseCase : KoinComponent {
    private val repository: AuthRepository by inject()
    private val sharedSettingsHelper: SharedSettingsHelper by inject()

    suspend operator fun invoke(
        email: String,
        name: String,
        password: String
    ): MyResult<AuthResultData> {
        if (name.isBlank()) {
            return MyResult.Error(
                message = "Invalid name"
            )
        }
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

        val authResult = repository.signUp(name, email, password)
        sharedSettingsHelper.token = authResult.data?.token?.accessToken ?: ""
        sharedSettingsHelper.refreshToken = authResult.data?.token?.refreshToken?.refreshToken ?: ""

        return authResult
    }
}