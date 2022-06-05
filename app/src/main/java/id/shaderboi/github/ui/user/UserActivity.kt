package id.shaderboi.github.ui.user

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.R
import id.shaderboi.github.databinding.ActivityUserBinding
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.userBrief = intent.getParcelableExtra("user")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)

        val menuItemShare = menu.findItem(R.id.menuItemShare)

        menuItemShare.setOnMenuItemClickListener { _ ->
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(this, Uri.parse(viewModel.userBrief.htmlUrl))

            true
        }

        return true
    }

    @StringRes
    private val tabTitles = intArrayOf(R.string.following, R.string.followers)

    private fun setupView() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.getUser(viewModel.userBrief.login)
            }

            launch {
                viewModel.isUserFavorited(viewModel.userBrief.id).collectLatest { isFavorited ->
                    val floatingActionButtonIcon = if (isFavorited) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                    binding.floatingActionButton.setImageResource(floatingActionButtonIcon)
                }
            }

            launch {
                viewModel.user.collectLatest { res ->
                    binding.apply {
                        if (res is Resource.Loading) {
                            shimmerFrameLayoutMain.startShimmer()

                            textViewUsername.setBackgroundResource(R.drawable.placeholder)
                            textViewName.setBackgroundResource(R.drawable.placeholder)
                        } else {
                            shimmerFrameLayoutMain.hideShimmer()

                            textViewUsername.background = null
                            textViewName.background = null
                        }

                        constraintLayoutError.isVisible = res is Resource.Error
                        shimmerFrameLayoutMain.isVisible = res !is Resource.Error

                        when (res) {
                            is Resource.Loading -> {}
                            is Resource.Error -> {
                                textViewErrorMessage.text = res.error.toString()
                            }
                            is Resource.Loaded -> {
                                val user = res.data

                                linearLayoutMiscInfo.removeAllViews()
                                user.location?.let { location ->
                                    linearLayoutMiscInfo.addView(
                                        TextView(this@UserActivity).apply {
                                            text = location
                                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                        }
                                    )
                                }
                                user.company?.let { company ->
                                    linearLayoutMiscInfo.addView(
                                        TextView(this@UserActivity).apply {
                                            text = company
                                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                        }
                                    )
                                }


                                textViewFollowerCount.text = user.followers.toString()
                                textViewFollowingCount.text = user.following.toString()

                                imageView.load(user.avatarUrl) {
                                    crossfade(true)
                                    placeholder(R.drawable.placeholder)
                                }
                                textViewUsername.text = user.login
                                textViewName.text = user.name
                            }
                        }
                    }
                }
            }

            binding.floatingActionButton.setOnClickListener {
                viewModel.toggleUserFavorite(viewModel.userBrief)
            }
            binding.viewPager2Content.adapter =
                SectionsPageAdapter(
                    supportFragmentManager,
                    lifecycle
                )
            TabLayoutMediator(binding.tabLayoutTabs, binding.viewPager2Content) { tab, position ->
                tab.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }
}