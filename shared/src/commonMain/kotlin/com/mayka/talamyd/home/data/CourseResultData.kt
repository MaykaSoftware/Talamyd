package com.mayka.talamyd.home.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class CourseResultData(
    val courses: List<CourseData>
): Parcelable

@Parcelize
data class CourseData(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val courseCode: String
): Parcelable