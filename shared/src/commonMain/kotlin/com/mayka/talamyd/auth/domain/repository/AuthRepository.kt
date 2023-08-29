package com.mayka.talamyd.auth.domain.repository

import com.mayka.talamyd.auth.domain.model.AuthResultData
import com.mayka.talamyd.auth.domain.model.TokenResultData
import com.mayka.talamyd.common.util.MyResult

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): MyResult<AuthResultData>

    suspend fun signIn(email: String, password: String): MyResult<AuthResultData>

    suspend fun refreshToken(refreshToken: String): MyResult<TokenResultData>
}