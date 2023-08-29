package com.mayka.talamyd.auth.data

import com.mayka.talamyd.auth.domain.model.AuthResultData
import com.mayka.talamyd.auth.domain.model.RefreshTokenResultData
import com.mayka.talamyd.auth.domain.model.TokenResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(id, name, bio, avatar, tokenPair.toTokenPairResultData())
}

internal fun RefreshResponseData.toTokenPairResultData(): TokenResultData {
    return TokenResultData(accessToken, refreshTokenData.toRefreshTokenResultData())
}

internal fun RefreshTokenResponseData.toRefreshTokenResultData(): RefreshTokenResultData {
    return RefreshTokenResultData(refreshToken, expiresAt)
}