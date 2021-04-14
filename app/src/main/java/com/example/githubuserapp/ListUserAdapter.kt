package com.example.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.databinding.ItemUsersBinding

class ListUserAdapter(private val listUser: ArrayList<Users>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        var tvUserName: TextView = binding.tvUsersUsernames
        var tvLinks: TextView = binding.tvUsersLinks
        var imgUser: ImageView = binding.usersProfpic
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
      val user = listUser[position]
      with(holder){
        Glide.with(itemView.context)
                .load(user.photo)
                .apply(RequestOptions().override(80, 80))
                .into(holder.imgUser)

        tvUserName.text = user.usernames
        tvLinks.text = user.link

        itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
      }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: Users)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}