package ru.shumilova.githubclient.mvp.presenter

import android.util.Log
import moxy.MvpPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.GithubUserRepo
import ru.shumilova.githubclient.mvp.presenter.list.IUserListPresenter
import ru.shumilova.githubclient.mvp.view.IUserItemView
import ru.shumilova.githubclient.mvp.view.IUsersView
import ru.terrakok.cicerone.Router
import java.util.*


class UsersPresenter : MvpPresenter<IUsersView?>() {
    private val usersRepo: GithubUserRepo = GithubUserRepo()
    private val router: Router? = GithubApplication.application?.router

    inner class UsersListPresenter : IUserListPresenter {
        val users: MutableList<GithubUser> = ArrayList()

        override fun onItemClick(view: IUserItemView) {
            Log.v(TAG, " onItemClick " + view.pos)
        }

        override fun bindView(view: IUserItemView) {
            val user: GithubUser = users[view.pos]
            view.setLogin(user.login)
        }

        override val count: Int
            get() = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.init()
        loadData()
    }

    private fun loadData() {
        val users: List<GithubUser> = usersRepo.users
        usersListPresenter.users.addAll(users)
        viewState?.updateList()
    }

    fun backPressed(): Boolean {
        router?.exit()
        return true
    }

    companion object {
        private val TAG = UsersPresenter::class.java.simpleName
        private const val VERBOSE = true
    }
}