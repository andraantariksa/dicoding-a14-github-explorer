package id.shaderboi.github.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.databinding.ActivityMainBinding
import id.shaderboi.github.domain.util.Resource
import id.shaderboi.github.ui.user.UserActivity
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.users.collectLatest { res ->
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
                            recyclerViewUsers.adapter = UsersAdapter(res.data) { view, user ->
                                val intent =
                                    Intent(this@MainActivity, UserActivity::class.java).apply {
                                        putExtra("user", user)
                                    }
                                startActivity(intent)
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