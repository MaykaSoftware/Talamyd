package com.mayka.talamyd.auth.domain

import com.mayka.talamyd.auth.data.AuthResultData
import com.mayka.talamyd.common.util.MyResult

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): MyResult<AuthResultData>

    suspend fun signIn(email: String, password: String): MyResult<AuthResultData>
}