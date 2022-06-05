package id.shaderboi.github.ui.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.shaderboi.github.ui.user.content.FollowersFragment
import id.shaderboi.github.ui.user.content.FollowingFragment

class SectionsPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = arrayOf(
        FollowingFragment(),
        FollowersFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}