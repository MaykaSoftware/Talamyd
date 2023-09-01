package com.mayka.talamyd.common.data

import com.mayka.talamyd.auth.data.RefreshTokenRequest
import com.mayka.talamyd.auth.data.RefreshTokenResponse
import com.mayka.talamyd.auth.data.TokenResultData
import com.mayka.talamyd.utils.SharedSettingsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val BASE_URL = "http://192.168.2.3:8080/"

abstract class KtorApi : KoinComponent {
    private val sharedSettingsHelper: SharedSettingsHelper by inject()

    val client = HttpClient {
        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("HttpClient: $message")
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

        install(Auth) {
            bearer {
                refreshTokens {
                    val token = client.post {
                        markAsRefreshTokenRequest()
                        endPoint(path = "refresh")
                        setBody(RefreshTokenRequest(sharedSettingsHelper.refreshToken))
                    }.body<RefreshTokenResponse>()

                    println("__TOKEN access = ${token.data?.accessToken}")
                    println("__TOKEN refresh = ${token.data?.refreshTokenData?.refreshToken}")
                    sharedSettingsHelper.token = "${token.data?.accessToken}"
                    sharedSettingsHelper.refreshToken = token.data?.refreshTokenData?.refreshToken ?: ""

                    BearerTokens(
                        accessToken = "${token.data?.accessToken}",
                        refreshToken = token.data?.refreshTokenData?.refreshToken ?: ""
                    )
                }
            }
        }
    }

    fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
       // if(!path.contains("refresh")) {
            headers {
                append(HttpHeaders.Authorization, sharedSettingsHelper.token)
            }
       // }
    }
}