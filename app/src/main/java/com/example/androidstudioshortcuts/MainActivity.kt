package com.example.androidstudioshortcuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidstudioshortcuts.data.Datasource
import com.example.androidstudioshortcuts.ui.theme.AndroidStudioShortcutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStudioShortcutsTheme {
                ShortcutApp()
            }
        }
    }
}

@Composable
fun ShortcutApp() {
    Scaffold(topBar = { ShortcutTopAppBar() }) {
        ShortcutList(shortcuts = Datasource.shortcuts, Modifier.padding(it))
    }
}
