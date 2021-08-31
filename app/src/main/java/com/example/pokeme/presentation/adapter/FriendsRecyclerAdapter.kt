package com.example.pokeme.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeme.R
import com.example.pokeme.data.models.Account

class FriendsRecyclerAdapter(private var friends: ArrayList<Account>) :
 RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val friendNameView: TextView = view.findViewById(R.id.friendUsername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friends_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = friends[position]
        holder.friendNameView.text = current.email
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    fun add(new: Account) {
        friends.add(new)
        notifyDataSetChanged()
    }

    fun add(new: List<Account>) {
        friends = new as ArrayList<Account>
        notifyDataSetChanged()
    }
}