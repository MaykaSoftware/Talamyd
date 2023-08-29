package com.mayka.talamyd.settings.component

import com.arkivanov.decompose.ComponentContext

interface SettingsComponent {
}

class SettingsComponentImpl(
    componentContext: ComponentContext
) : SettingsComponent, ComponentContext by componentContext {
}