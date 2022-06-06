package id.shaderboi.github.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.github.databinding.ActivityFavoriteBinding
import id.shaderboi.github.ui.main.UsersAdapter
import id.shaderboi.github.ui.main.UsersShimmerAdapter
import id.shaderboi.github.ui.user.UserActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        binding.recyclerViewUsers.adapter = UsersShimmerAdapter()
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.listFavorite.collectLatest { favorites ->
                    binding.shimmerFrameLayoutMain.hideShimmer()
                    binding.recyclerViewUsers.adapter = UsersAdapter(favorites) { _, user ->
                        startActivity(
                            Intent(this@FavoriteActivity, UserActivity::class.java).apply {
                                putExtra("user", user)
                            }
                        )
                    }
                }
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) or
                    makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = viewHolder as UsersAdapter.ViewHolder

                viewModel.removeFavorite(user.id)

                binding.recyclerViewUsers.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recyclerViewUsers)
    }
}