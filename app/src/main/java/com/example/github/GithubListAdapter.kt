package com.example.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.models.RepositoryEntity
import com.squareup.picasso.Picasso

class GithubListAdapter(
        private val data: List<RepositoryEntity>,
        val onRepoClickListener: OnRepoClickListener
    ): RecyclerView.Adapter<GithubListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)

        return ViewHolder(view, onRepoClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(data[position].owner?.avatarUrl).into(holder.avatar)
        holder.repoName.text = data[position].fullName
        holder.author.text = data[position].owner?.login
        holder.watcher.text = data[position].watchers.toString()
        holder.forks.text = data[position].forks.toString()
        holder.issues.text = data[position].issues.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View, val listener: OnRepoClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var avatar: ImageView = view.findViewById(R.id.avatar)
        var repoName: TextView = view.findViewById(R.id.repoName)
        var author: TextView = view.findViewById(R.id.author)
        var watcher: TextView = view.findViewById(R.id.watcher)
        var forks: TextView = view.findViewById(R.id.fork)
        var issues: TextView = view.findViewById(R.id.issues)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }

    }

    interface OnRepoClickListener {
        fun onItemClick(position: Int)
    }
}