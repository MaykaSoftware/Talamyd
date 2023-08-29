package com.mayka.talamyd.auth.domain.repository

import com.mayka.talamyd.auth.data.AuthService
import com.mayka.talamyd.auth.data.RefreshTokenRequest
import com.mayka.talamyd.auth.data.SignInRequest
import com.mayka.talamyd.auth.data.SignUpRequest
import com.mayka.talamyd.auth.data.toAuthResultData
import com.mayka.talamyd.auth.data.toTokenPairResultData
import com.mayka.talamyd.auth.domain.model.AuthResultData
import com.mayka.talamyd.auth.domain.model.TokenResultData
import com.mayka.talamyd.common.util.DispatcherProvider
import com.mayka.talamyd.common.util.MyResult
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService
) : AuthRepository {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): MyResult<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                val request = SignUpRequest(name, email, password)

                val authResponse = authService.signUp(request)

                if (authResponse.data == null) {
                    MyResult.Error(
                        message = authResponse.errorMessage!!
                    )
                } else {
                    MyResult.Success(
                        data = authResponse.data.toAuthResultData()
                    )
                }
            } catch (e: Exception) {
                MyResult.Error(
                    message = "Oops, we could not send your request, try later!"
                )
            }
        }
    }

    override suspend fun signIn(email: String, password: String): MyResult<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                val request = SignInRequest(email, password)

                val authResponse = authService.signIn(request)

                if (authResponse.data == null) {
                    MyResult.Error(
                        message = authResponse.errorMessage!!
                    )
                } else {
                    MyResult.Success(
                        data = authResponse.data.toAuthResultData()
                    )
                }
            } catch (e: Exception) {
                MyResult.Error(
                    message = "Oops, we could not send your request, try later!"
                )
            }
        }
    }

    override suspend fun refreshToken(refreshToken: String): MyResult<TokenResultData> {
        return withContext(dispatcher.io) {
            try {
                val request = RefreshTokenRequest(refreshToken)
                val tokenResponse = authService.refresh(request)

                if (tokenResponse.data == null) {
                    MyResult.Error(
                        message = tokenResponse.errorMessage!!
                    )
                } else {
                    MyResult.Success(
                        data = tokenResponse.data.toTokenPairResultData()
                    )
                }
            } catch (e: Exception) {
                MyResult.Error(
                    message = "Oops, we could refresh your token, try later!"
                )
            }
        }
    }
}