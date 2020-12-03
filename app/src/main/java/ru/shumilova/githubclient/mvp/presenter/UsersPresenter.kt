package ru.shumilova.githubclient.mvp.presenter

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.repository.IGithubUsersRepo
import ru.shumilova.githubclient.mvp.presenter.list.IUserListPresenter
import ru.shumilova.githubclient.mvp.view.IUserItemView
import ru.shumilova.githubclient.mvp.view.IUsersView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UsersPresenter(
    private val mainThreadScheduler: Scheduler
) : MvpPresenter<IUsersView>() {

    @Inject
    lateinit var userRepo: IGithubUsersRepo
    @Inject
    lateinit var router: Router

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((Int) -> Unit)? = null

        override val count: Int
            get() = users.size

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }

        override fun onItemClick(view: IUserItemView) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.pos)
            }

        }

    }

    val usersListPresenter = UsersListPresenter()
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.init()
        loadData()

        usersListPresenter.itemClickListener = { pos ->
            val user = usersListPresenter.users[pos]
            router?.navigateTo(Screens.UserScreen(user))
        }
    }

    private fun loadData() {
        userRepo.getUsers()
            .observeOn(mainThreadScheduler)
            .subscribe({ userRepo ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(userRepo)
                viewState.updateList()
            }, { println("Error: ${it.message}") })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
