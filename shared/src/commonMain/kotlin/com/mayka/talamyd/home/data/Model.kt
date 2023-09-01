package com.mayka.talamyd.home.data

import kotlinx.serialization.Serializable

@Serializable
data class CourseRequest(
    val courseLevel: Int
)

@Serializable
data class CourseResponse(
    val data: List<Course> = emptyList(),
    val errorMessage: String? = null
)

@Serializable
data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val courseCode: String
)