package com.mayka.talamyd.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.koin.core.component.KoinComponent

interface TalamydAuthComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Signup(val component: SignupComponent) : Child()
        class Login(val component: LoginComponent) : Child()
        class Guest(val component: GuestComponent) : Child()
    }
}

class TalamydAuthComponentImpl(
    componentContext: ComponentContext,
    private val onLoginSuccess: () -> Unit
) : TalamydAuthComponent, KoinComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, TalamydAuthComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TalamydAuthComponent.Child =
        when (config) {
            Config.Guest -> TalamydAuthComponent.Child.Guest(
                GuestComponentImpl(componentContext)
            )

            Config.Login ->
                TalamydAuthComponent.Child.Login(
                    LoginComponentImpl(
                        componentContext = componentContext,
                        onLoginSuccess = { onLoginSuccess() },
                        onRegister = { navigation.push(Config.Signup) }
                    )
                )

            Config.Signup -> TalamydAuthComponent.Child.Signup(
                SignupComponentImpl(
                    componentContext,
                    onSignupSuccess = {
                        navigation.pop()
                    },
                    onCloseClick = {
                        navigation.pop()
                    }
                )
            )
        }

    @Parcelize
    sealed class Config : Parcelable {
        data object Signup : Config()
        data object Login : Config()
        data object Guest : Config()
    }
}