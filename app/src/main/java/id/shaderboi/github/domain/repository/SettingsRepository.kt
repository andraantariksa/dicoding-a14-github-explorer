package id.shaderboi.github.domain.repository

import id.shaderboi.github.domain.model.Settings
import id.shaderboi.github.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>
    suspend fun setTheme(theme: Theme)
}