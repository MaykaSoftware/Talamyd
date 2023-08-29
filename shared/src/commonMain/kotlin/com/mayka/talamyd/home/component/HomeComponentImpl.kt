package com.mayka.talamyd.home.component

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {
}

class HomeComponentImpl(
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {
}