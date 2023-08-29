package com.mayka.talamyd.common.data

import com.mayka.talamyd.auth.data.RefreshTokenRequest
import com.mayka.talamyd.auth.domain.model.TokenResultData
import com.mayka.talamyd.utils.SharedSettingsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val BASE_URL = "http://192.168.2.3:8080/"

internal abstract class KtorApi : KoinComponent {
    private val sharedSettingsHelper: SharedSettingsHelper by inject()

    val client = HttpClient {
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
                    val token = client.get {
                        markAsRefreshTokenRequest()
                        endPoint(path = "refresh")
                        parameter("", RefreshTokenRequest(sharedSettingsHelper.refreshToken))
                    }.body<TokenResultData>()

                    sharedSettingsHelper.token = token.accessToken
                    sharedSettingsHelper.refreshToken = token.refreshToken.refreshToken

                    BearerTokens(
                        accessToken = token.accessToken,
                        refreshToken = token.refreshToken.refreshToken
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
        header("Authorization", sharedSettingsHelper.token)
    }
}