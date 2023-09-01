package com.mayka.talamyd.home.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.home.data.CourseData
import com.mayka.talamyd.home.domain.GetCoursesUseCase
import com.mayka.talamyd.utils.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@Parcelize
data class CoursesUiState(
    val courses: List<CourseData> = emptyList(),
    val authErrorMessage: String? = null,
) : Parcelable

interface CoursesComponent {
    val uiState: Value<CoursesUiState>

    fun getCourses()
}

class CoursesComponentImpl(
    componentContext: ComponentContext
) : CoursesComponent, KoinComponent, ComponentContext by componentContext {

    private val useCase: GetCoursesUseCase by inject()
    private val coroutineScope = coroutineScope()

    private val _uiState = MutableValue(CoursesUiState())
    override val uiState: Value<CoursesUiState> = _uiState

    init {
        getCourses()
    }

    override fun getCourses() {
        coroutineScope.launch {
            when(val result = useCase.invoke()){
                is MyResult.Error -> {
                    _uiState.update { it.copy(authErrorMessage = result.message) }
                }
                is MyResult.Success -> {
                    _uiState.update { it.copy(courses = result.data!!.courses) }
                    println(result.data!!.courses)
                }
            }
        }
    }
}