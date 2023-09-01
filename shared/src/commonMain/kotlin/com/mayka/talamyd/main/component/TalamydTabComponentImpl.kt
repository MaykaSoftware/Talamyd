package com.mayka.talamyd.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mayka.talamyd.home.component.CoursesComponentImpl
import com.mayka.talamyd.price.component.PriceComponentImpl
import com.mayka.talamyd.settings.component.SettingsComponentImpl

interface TalamydTabComponent {
    val childStack: Value<ChildStack<*, Child>>
    val dialogSlot: Value<ChildSlot<*, SignOutComponent>>

    fun onHomeTabClicked()
    fun onPricesTabClicked()
    fun onSettingsTabClicked()
    fun onSignOutClicked()

    sealed class Child {
        class HomeChild(val component: CoursesComponentImpl) : Child()
        class PricesChild(val component: PriceComponentImpl) : Child()
        class SettingsChild(val component: SettingsComponentImpl) : Child()
    }
}

class TalamydTabComponentImpl(
    componentContext: ComponentContext,
    private val onSignOut: () -> Unit
) : TalamydTabComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): TalamydTabComponent.Child =
        when (config) {
            Config.Home -> TalamydTabComponent.Child.HomeChild(CoursesComponentImpl(componentContext))
            Config.Price -> TalamydTabComponent.Child.PricesChild(
                PriceComponentImpl(
                    componentContext
                )
            )

            Config.Setting -> TalamydTabComponent.Child.SettingsChild(
                SettingsComponentImpl(
                    componentContext
                )
            )
        }

    private val stack =
        childStack(
            source = navigation,
            initialStack = { listOf(Config.Home) },
            childFactory = ::child,
        )

    override val childStack: Value<ChildStack<*, TalamydTabComponent.Child>>
        get() = stack

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    private val _dialogSlot =
        childSlot<DialogConfig, SignOutComponent>(
            source = dialogNavigation,
            persistent = false,
            handleBackButton = true,
            childFactory = { config, childComponentContext ->
                SignOutComponentImpl(
                    componentContext = childComponentContext,
                    title = "Log out",
                    message = config.message,
                    onDismissed = dialogNavigation::dismiss,
                    onSignOut = onSignOut
                )
            }
        )

    override val dialogSlot: Value<ChildSlot<*, SignOutComponent>> = _dialogSlot

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onPricesTabClicked() {
        navigation.bringToFront(Config.Price)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Setting)
    }

    override fun onSignOutClicked() {
        dialogNavigation.activate(DialogConfig(message = "Do you really want to log out"))
    }

    @Parcelize
    private data class DialogConfig(
        val message: String,
    ) : Parcelable

    @Parcelize
    private sealed class Config : Parcelable {
        object Home : Config()
        object Price : Config()
        object Setting : Config()
    }
}