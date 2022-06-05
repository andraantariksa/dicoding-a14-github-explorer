package id.shaderboi.github.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import id.shaderboi.github.domain.model.Settings
import id.shaderboi.github.domain.model.Theme
import id.shaderboi.github.domain.repository.SettingsRepository
import id.shaderboi.github.util.THEME
import id.shaderboi.github.util.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(@ApplicationContext private val context: Context) :
    SettingsRepository {
    override fun getSettings(): Flow<Settings> = context.settingsDataStore.data.map { prefs ->
        Settings(theme = Theme.valueOf(prefs[THEME] ?: "Default"))
    }

    override suspend fun setTheme(theme: Theme) {
        context.settingsDataStore.edit { prefs ->
            prefs[THEME] = theme.toString()
        }
    }
}