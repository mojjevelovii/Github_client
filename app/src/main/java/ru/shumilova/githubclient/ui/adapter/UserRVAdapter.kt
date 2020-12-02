package ru.shumilova.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_user.view.tv_login
import kotlinx.android.synthetic.main.item_user.view.*
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.presenter.list.IUserListPresenter
import ru.shumilova.githubclient.mvp.view.IUserItemView
import ru.shumilova.githubclient.mvp.view.image.IImageLoader


class UserRVAdapter(
    val presenter: IUserListPresenter,
    val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<UserRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(position) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.count

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, IUserItemView {
        override var pos = -1
        override fun setLogin(text: String?) = containerView.run { tv_login.text = text }
        override fun loadAvatar(url: String) =
            containerView.run { imageLoader.loadInto(url, iv_avatar) }
    }
}




