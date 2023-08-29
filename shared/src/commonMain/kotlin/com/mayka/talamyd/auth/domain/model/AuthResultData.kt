package com.mayka.talamyd.auth.domain.model

data class AuthResultData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: TokenResultData
)

data class TokenResultData(
    val accessToken: String,
    val refreshToken: RefreshTokenResultData
)

data class RefreshTokenResultData(
    val refreshToken: String,
    val expireAt: Long
)

