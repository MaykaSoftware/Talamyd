package com.mayka.talamyd.auth.component

import com.arkivanov.decompose.ComponentContext

interface GuestComponent

class GuestComponentImpl(
    componentContext: ComponentContext
) : GuestComponent, ComponentContext by componentContext {
}