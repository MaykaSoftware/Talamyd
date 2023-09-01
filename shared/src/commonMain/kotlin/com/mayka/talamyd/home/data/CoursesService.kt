package com.mayka.talamyd.home.data

import com.mayka.talamyd.common.data.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CoursesService : KtorApi() {
    suspend fun courses(request: CourseRequest): CourseResponse = client.get {
        endPoint("courses")
        parameter("level", request.courseLevel)
    }.body()
}