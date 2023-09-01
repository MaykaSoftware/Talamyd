package com.mayka.talamyd.home.domain

import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.home.data.CourseResultData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCoursesUseCase : KoinComponent {
    private val repository: CoursesRepository by inject()

    suspend operator fun invoke(): MyResult<CourseResultData> {
        return repository.getCourses(8)
    }
}