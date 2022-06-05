package id.shaderboi.github

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.github.domain.repository.SettingsRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class GithubExplorerApplication : Application() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    companion object {
        var applicationScope = MainScope()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel("onLowMemory() called by system")
        applicationScope = MainScope()
    }

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            settingsRepository.getSettings().collectLatest { settings ->
                AppCompatDelegate.setDefaultNightMode(settings.theme.toAppCompatDelegate())
            }
        }
    }
}