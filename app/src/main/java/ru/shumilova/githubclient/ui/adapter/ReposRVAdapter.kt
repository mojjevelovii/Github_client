package ru.shumilova.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.view.*
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.model.entity.UserRepo

class ReposRVAdapter(private val listener: (UserRepo) -> Unit) :
    RecyclerView.Adapter<ReposRVAdapter.RepoViewHolder>() {
    var data: List<UserRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RepoViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(userRepo: UserRepo) {
            containerView.tv_repo_name.text = userRepo.name
            containerView.setOnClickListener { listener.invoke(userRepo) }
        }
    }
}