package com.mayka.talamyd.auth.data

fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(id, name, bio, avatar, tokenPair.toTokenPairResultData())
}

fun RefreshResponseData.toTokenPairResultData(): TokenResultData {
    return TokenResultData(accessToken, refreshTokenData.toRefreshTokenResultData())
}

fun RefreshTokenResponseData.toRefreshTokenResultData(): RefreshTokenResultData {
    return RefreshTokenResultData(refreshToken, expiresAt)
}
