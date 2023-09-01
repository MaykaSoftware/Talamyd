package com.mayka.talamyd.home.domain

import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.home.data.CourseResponse
import com.mayka.talamyd.home.data.CourseResultData

interface CoursesRepository {
    suspend fun getCourses(courseLevel: Int): MyResult<CourseResultData>
}