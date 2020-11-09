package ru.shumilova.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.presenter.list.IUserListPresenter
import ru.shumilova.githubclient.mvp.view.IUserItemView


class UserRVAdapter(var presenter: IUserListPresenter?) :
    RecyclerView.Adapter<UserRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val userView: View = inflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(userView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.pos = position
        holder.itemView.setOnClickListener { presenter?.onItemClick(holder) }
        presenter?.bindView(holder)
    }

    override fun getItemCount(): Int {
        return presenter?.count ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        IUserItemView {
        var textView: TextView = itemView.findViewById<View>(R.id.tv_login) as TextView
        override var pos = 0

        override fun setLogin(text: String?) {
            textView.text = text
        }

    }

}
