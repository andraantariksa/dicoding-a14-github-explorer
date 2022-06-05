package id.shaderboi.github.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.shaderboi.github.R
import id.shaderboi.github.databinding.ItemUserBinding
import id.shaderboi.github.domain.model.UserBrief

class UsersAdapter(
    private val userBriefs: List<UserBrief>,
    private val onClickListener: ((view: View, userBrief: UserBrief) -> Unit)? = null
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding, var id: Int) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, -1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userBriefs[position]

        holder.id = user.id
        holder.binding.apply {
            textViewUsername.text = user.login
            imageViewAvatar.load(user.avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            root.setOnClickListener { view ->
                onClickListener?.invoke(view, user)
            }
        }
    }

    override fun getItemCount(): Int = userBriefs.size
}