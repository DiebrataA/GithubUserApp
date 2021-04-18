package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.databinding.ItemUsersBinding
import com.example.githubuserapp.model.Users

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val listUser = ArrayList<Users>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Users>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(users)
            }
            with(binding) {
                Glide.with(root.context)
                        .load(users.photo)
                        .apply(RequestOptions().override(150, 150))
                        .into(usersProfpic)
                tvUsersUsernames.text = users.usernames
                tvUsersLinks.text = users.link


                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(users) }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}