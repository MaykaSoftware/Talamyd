package com.mayka.talamyd

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = TalamydRootComponentImpl(
            componentContext = defaultComponentContext()
        )
        setContent {
            ContentController(rootComponent)
        }
    }
}