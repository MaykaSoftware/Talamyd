package com.mayka.talamyd

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mayka.talamyd.auth.component.TalamydAuthComponent
import com.mayka.talamyd.auth.component.TalamydAuthComponentImpl
import com.mayka.talamyd.main.component.TalamydMainComponent
import com.mayka.talamyd.main.component.TalamydMainComponentImpl
import com.mayka.talamyd.utils.SharedSettingsHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TalamydRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onSignOut()

    sealed class Child {
        object Loading : Child()
        class Auth(val component: TalamydAuthComponent) : Child()
        class Main(val component: TalamydMainComponent) : Child()
    }
}

class TalamydRootComponentImpl(
    componentContext: ComponentContext
) : TalamydRootComponent, KoinComponent, ComponentContext by componentContext {

    private val sharedSettingsHelper: SharedSettingsHelper by inject()
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, TalamydRootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Loading,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onSignOut() {
        sharedSettingsHelper.token = ""
        showAuthenticationFlow()
    }

    init {
        if (sharedSettingsHelper.token.isNotEmpty()) {
            showMain()
        } else {
            showAuthenticationFlow()
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TalamydRootComponent.Child =
        when (config) {
            Config.Loading -> TalamydRootComponent.Child.Loading
            Config.Auth -> TalamydRootComponent.Child.Auth(
                TalamydAuthComponentImpl(
                    componentContext,
                    onLoginSuccess = {
                        showMain()
                    }
                ))

            Config.Main -> TalamydRootComponent.Child.Main(TalamydMainComponentImpl(
                componentContext = componentContext,
                onSignOut = { onSignOut() }
            ))
        }

    private fun showMain() {
        navigation.replaceAll(Config.Main)
    }

    private fun showAuthenticationFlow() {
        navigation.replaceAll(Config.Auth)
    }

    @Parcelize
    private sealed class Config : Parcelable {
        data object Loading : Config()
        data object Auth : Config()
        data object Main : Config()
    }
}