package com.mayka.talamyd.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResultData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: TokenResultData
)



@Serializable
data class TokenResultData(
    val accessToken: String? = null,
    val refreshTokenData: RefreshTokenResultData? = null
)

@Serializable
data class RefreshTokenResultData(
    val refreshToken: String? = null,
    val expireAt: Long? = null
)

