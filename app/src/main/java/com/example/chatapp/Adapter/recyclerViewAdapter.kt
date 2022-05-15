package com.example.chatapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Model.ChatModel
import com.example.chatapp.databinding.ActivityChatScreenBinding
import com.example.chatapp.databinding.ChatRowBinding

class recyclerViewAdapter(val liste:ArrayList<ChatModel>) : RecyclerView.Adapter<recyclerViewAdapter.ChatHolder>() {
    class ChatHolder(val binding: ChatRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val binding = ChatRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.binding.ChatText.text = liste.get(position).chatText
        holder.binding.UserNameTextView.text = liste.get(position).email
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}