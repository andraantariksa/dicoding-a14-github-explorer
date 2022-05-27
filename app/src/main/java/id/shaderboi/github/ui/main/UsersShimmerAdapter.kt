package id.shaderboi.github.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.github.R
import id.shaderboi.github.databinding.ItemUserBinding

class UsersShimmerAdapter : RecyclerView.Adapter<UsersShimmerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            textViewUsername.setBackgroundResource(R.drawable.placeholder)
            imageViewAvatar.setBackgroundResource(R.drawable.placeholder)
        }
    }

    override fun getItemCount(): Int = 20
}