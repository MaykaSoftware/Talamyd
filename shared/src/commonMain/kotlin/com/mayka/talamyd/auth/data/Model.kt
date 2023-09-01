package com.mayka.talamyd.auth.data

import kotlinx.serialization.Serializable

//Request
@Serializable
data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)


//Response
@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null
)

@Serializable
data class AuthResponseData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val tokenPair: RefreshResponseData,
)

@Serializable
data class RefreshTokenResponse(
    val data: RefreshResponseData?,
    val errorMessage: String?
)

@Serializable
data class RefreshResponseData(
    val accessToken: String,
    val refreshTokenData: RefreshTokenResponseData
)

@Serializable
data class RefreshTokenResponseData(
    val refreshToken: String,
    val expiresAt: Long
)
