package ru.shumilova.githubclient.mvp.presenter.list

import ru.shumilova.githubclient.mvp.view.IItemView

interface IListPresenter<V : IItemView?> {
    fun onItemClick(view: V)
    fun bindView(view: V)
    val count: Int
}