package id.shaderboi.github.domain.model

import androidx.appcompat.app.AppCompatDelegate

enum class Theme {
    Default,
    Dark,
    Light;

    fun toAppCompatDelegate() = when (this) {
        Default -> AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
        Dark -> AppCompatDelegate.MODE_NIGHT_YES
        Light -> AppCompatDelegate.MODE_NIGHT_NO
    }
}