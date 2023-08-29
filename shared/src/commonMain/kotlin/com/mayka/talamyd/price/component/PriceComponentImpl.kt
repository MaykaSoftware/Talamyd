package com.mayka.talamyd.price.component

import com.arkivanov.decompose.ComponentContext

interface PriceComponent {
}

class PriceComponentImpl(
    componentContext: ComponentContext
) : PriceComponent, ComponentContext by componentContext {
}