package com.mayka.talamyd.home.data

fun CourseResponse.toCourseResultData(): List<CourseData> {
    val courses = this.data.map {
        CourseData(it.id, it.title, it.description, it.imageUrl, it.courseCode)
    }
    return courses
}