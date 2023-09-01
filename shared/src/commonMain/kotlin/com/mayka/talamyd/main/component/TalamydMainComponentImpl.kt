package com.mayka.talamyd.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mayka.talamyd.settings.component.SettingsComponent
import com.mayka.talamyd.settings.component.SettingsComponentImpl

interface TalamydMainComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Tab(val component: TalamydTabComponent) : Child()
        class Settings(val component: SettingsComponent) : Child()
    }
}

class TalamydMainComponentImpl(
    componentContext: ComponentContext,
    private val onSignOut: () -> Unit,
) : TalamydMainComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, TalamydMainComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Tab,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): TalamydMainComponent.Child =
        when (config) {
            Config.Tab -> TalamydMainComponent.Child.Tab(
                TalamydTabComponentImpl(
                    componentContext = componentContext,
                    onSignOut = onSignOut
                )
            )

            Config.Settings -> TalamydMainComponent.Child.Settings(
                SettingsComponentImpl(
                    componentContext = componentContext
                )
            )
        }

    @Parcelize
    private sealed class Config : Parcelable {
        data object Tab : Config()
        data object Settings : Config()
    }
}