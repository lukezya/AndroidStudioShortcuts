package com.example.androidstudioshortcuts.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Shortcut(
    @StringRes val shortcutResId: Int,
    @StringRes val actionResId: Int,
    @DrawableRes val gifResId: Int
)
