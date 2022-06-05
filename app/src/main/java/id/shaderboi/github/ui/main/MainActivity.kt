package id.shaderboi.github.ui.main

import id.shaderboi.github.domain.repository.SettingsRepository
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.R
import id.shaderboi.github.databinding.ActivityMainBinding
import id.shaderboi.github.domain.model.Theme
import id.shaderboi.github.domain.util.Resource
import id.shaderboi.github.ui.user.UserActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItemSearch = menu.findItem(R.id.menuItemSearch)
        val menuItemThemeToggle = menu.findItem(R.id.menuItemThemeToggle)

        val searchView = menuItemSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryUsers(newText)
                return false
            }
        })

        lifecycleScope.launchWhenCreated {
            settingsRepository.getSettings().collectLatest { settings ->
                menuItemThemeToggle.icon =
                    AppCompatResources.getDrawable(
                        this@MainActivity,
                        if (settings.theme == Theme.Dark) R.drawable.ic_baseline_light_mode_24
                        else R.drawable.ic_baseline_dark_mode_24
                    )

                menuItemThemeToggle.setOnMenuItemClickListener { item ->
                    this@launchWhenCreated.launch {
                        settingsRepository.setTheme(
                            if (settings.theme == Theme.Dark) Theme.Light
                            else Theme.Dark
                        )
                    }
                    true
                }
            }
        }

        return true
    }

    private fun setupView() {
        lifecycleScope.launchWhenStarted {
            viewModel.users.collectLatest { res ->
                binding.apply {
                    if (res is Resource.Loading) {
                        shimmerFrameLayoutMain.startShimmer()
                    } else {
                        shimmerFrameLayoutMain.hideShimmer()
                    }

                    constraintLayoutError.isVisible = res is Resource.Error
                    shimmerFrameLayoutMain.isVisible = res !is Resource.Error

                    when (res) {
                        is Resource.Error -> {
                            textViewErrorMessage.text = res.error.toString()
                        }
                        is Resource.Loaded -> {
                            linearLayoutNoResult.isVisible = res.data.isEmpty()
                            recyclerViewUsers.isVisible = res.data.isNotEmpty()
                            if (res.data.isNotEmpty()) {
                                recyclerViewUsers.adapter = UsersAdapter(res.data) { view, user ->
                                    val intent =
                                        Intent(this@MainActivity, UserActivity::class.java).apply {
                                            putExtra("user", user)
                                        }
                                    startActivity(intent)
                                }
                            }
                        }
                        is Resource.Loading -> {
                            recyclerViewUsers.adapter = UsersShimmerAdapter()
                        }
                    }
                }
            }
        }
    }
}