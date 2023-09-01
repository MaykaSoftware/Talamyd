package com.mayka.talamyd.home.domain

import com.mayka.talamyd.common.util.DispatcherProvider
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.home.data.CourseRequest
import com.mayka.talamyd.home.data.CourseResultData
import com.mayka.talamyd.home.data.CoursesService
import com.mayka.talamyd.home.data.toCourseResultData
import kotlinx.coroutines.withContext

class CoursesRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val coursesService: CoursesService
) : CoursesRepository {
    override suspend fun getCourses(courseLevel: Int): MyResult<CourseResultData> {
        return withContext(dispatcher.io) {
            try {
                val courses = coursesService.courses(CourseRequest(courseLevel))
                MyResult.Success(CourseResultData(courses.toCourseResultData()))
            } catch (e: Exception) {
                MyResult.Error(
                    message = "Oops, we could not get the courses, try later!"
                )
            }
        }
    }
}