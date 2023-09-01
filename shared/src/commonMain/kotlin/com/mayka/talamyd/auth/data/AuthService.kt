package com.mayka.talamyd.auth.data

import com.mayka.talamyd.common.data.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthService : KtorApi() {
    suspend fun signUp(request: SignUpRequest): AuthResponse = client.post {
        endPoint(path = "signup")
        setBody(request)
    }.body()

    suspend fun signIn(request: SignInRequest): AuthResponse = client.post {
        endPoint("login")
        setBody(request)
    }.body()
}