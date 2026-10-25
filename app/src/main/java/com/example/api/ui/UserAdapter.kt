package com.example.api.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.api.data.model.User
import com.example.api.databinding.ItemUserBinding

class UserAdapter(private var users: List<User> = emptyList()) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun submitList(newList: List<User>) {
        users = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.name
            val formattedUsername = if (user.username.startsWith("@")) {
                user.username
            } else {
                "@${user.username}"
            }
            binding.tvUsername.text = formattedUsername
        }
    }
}
