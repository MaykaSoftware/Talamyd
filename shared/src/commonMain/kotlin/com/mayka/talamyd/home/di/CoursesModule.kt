package com.mayka.talamyd.home.di

import com.mayka.talamyd.home.data.CoursesService
import com.mayka.talamyd.home.domain.CoursesRepository
import com.mayka.talamyd.home.domain.CoursesRepositoryImpl
import com.mayka.talamyd.home.domain.GetCoursesUseCase
import org.koin.dsl.module

val courseModule = module {
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }
    factory { CoursesService() }
    factory { GetCoursesUseCase() }
}