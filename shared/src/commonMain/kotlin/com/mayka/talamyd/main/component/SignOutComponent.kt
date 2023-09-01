package com.mayka.talamyd.main.component

import com.arkivanov.decompose.ComponentContext

interface SignOutComponent {
    val title: String
    val message: String

    fun onDismissClicked()
    fun onSignOutClicked()
}

class SignOutComponentImpl(
    componentContext: ComponentContext,
    override val title: String,
    override val message: String,
    private val onDismissed: () -> Unit,
    private val onSignOut: () -> Unit
) : SignOutComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        onDismissed()
    }

    override fun onSignOutClicked() {
        onSignOut()
    }
}