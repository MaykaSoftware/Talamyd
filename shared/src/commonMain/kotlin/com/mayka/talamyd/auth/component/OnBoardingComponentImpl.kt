package com.mayka.talamyd.auth.component

import com.arkivanov.decompose.ComponentContext

interface OnBoardingComponent {
}

class OnBoardingComponentImpl(
    componentContext: ComponentContext
) : OnBoardingComponent, ComponentContext by componentContext {
}