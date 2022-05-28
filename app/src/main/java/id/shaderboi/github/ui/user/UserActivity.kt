package id.shaderboi.github.ui.user

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.R
import id.shaderboi.github.databinding.ActivityUserBinding
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private var _binding: ActivityUserBinding? = null
    val binding get() = _binding!!

    private lateinit var userBrief: UserBrief

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userBrief = intent.getParcelableExtra("user")!!

        _binding = ActivityUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemShare -> {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(this, Uri.parse(userBrief.htmlUrl))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        lifecycleScope.launchWhenStarted {
            userViewModel.getUser(userBrief.login)

            userViewModel.user.collectLatest { res ->
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
                                linearLayoutMiscInfo.addView(TextView(this@UserActivity).apply {
                                    text = location
                                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                })
                            }
                            user.company?.let { company ->
                                linearLayoutMiscInfo.addView(TextView(this@UserActivity).apply {
                                    text = company
                                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                                })
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
    }
}