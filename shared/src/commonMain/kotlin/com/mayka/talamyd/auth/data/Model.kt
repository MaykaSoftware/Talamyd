package com.mayka.talamyd.auth.data

import kotlinx.serialization.Serializable

//Request
@Serializable
internal data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
internal data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
internal data class RefreshTokenRequest(
    val refreshToken: String
)


//Response
@Serializable
internal data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null
)

@Serializable
internal data class AuthResponseData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val tokenPair: RefreshResponseData,
)

@Serializable
internal data class RefreshTokenResponse(
    val data: RefreshResponseData?,
    val errorMessage: String?
)

@Serializable
internal data class RefreshResponseData(
    val accessToken: String,
    val refreshTokenData: RefreshTokenResponseData
)

@Serializable
internal data class RefreshTokenResponseData(
    val refreshToken: String,
    val expiresAt: Long
)
