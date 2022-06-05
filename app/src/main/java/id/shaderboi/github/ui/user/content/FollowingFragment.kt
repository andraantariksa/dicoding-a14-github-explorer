package id.shaderboi.github.ui.user.content

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.databinding.FragmentUsersBinding
import id.shaderboi.github.domain.util.Resource
import id.shaderboi.github.ui.main.UsersAdapter
import id.shaderboi.github.ui.main.UsersShimmerAdapter
import id.shaderboi.github.ui.user.UserActivity
import id.shaderboi.github.ui.user.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding

    private val userViewModel by activityViewModels<UserViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        followingViewModel.fetchFollowing(userViewModel.userBrief.login)

        lifecycleScope.launchWhenStarted {
            followingViewModel.users.collectLatest { res ->
                binding.recyclerViewUsers.isVisible = res !is Resource.Error
                binding.constraintLayoutError.isVisible = res is Resource.Error

                when (res) {
                    is Resource.Loaded -> {
                        binding.recyclerViewUsers.adapter = UsersAdapter(res.data) { _, user ->
                            val intent =
                                Intent(requireContext(), UserActivity::class.java).apply {
                                    putExtra("user", user)
                                }
                            startActivity(intent)
                        }
                    }
                    is Resource.Loading -> {
                        binding.recyclerViewUsers.adapter = UsersShimmerAdapter()
                    }
                    is Resource.Error -> {
                        binding.textViewErrorMessage.text = res.error.toString()
                    }
                }
            }
        }
    }
}