package com.example.postviewersapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postviewersapp.databinding.ItemPostBinding

import com.example.postviewersapp.models.PostData

class PostsAdapter(
    private val context: Context,
    private val posts: List<PostData>
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=posts[position]
        holder.title.text=currentItem.title
        holder.body.text=currentItem.body
        val isVisible:Boolean=currentItem.expand
        holder.isExpand.visibility=if(isVisible)View.VISIBLE else View.GONE
        holder.downArrow.setOnClickListener{
            currentItem.expand=!currentItem.expand
            notifyItemChanged(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.user_title)
        val body:TextView = itemView.findViewById(R.id.user_body)
        val isExpand:LinearLayout = itemView.findViewById(R.id.hidden_view)
        val downArrow:ImageButton=itemView.findViewById(R.id.imageButton1)



    }
}