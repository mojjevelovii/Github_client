package ru.shumilova.githubclient.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.repository.IGithubUsersRepo
import ru.shumilova.githubclient.mvp.view.IUserView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router

class UserPresenter(
    private val router: Router,
    private val userRepo: IGithubUsersRepo,
    private val mainThreadScheduler: Scheduler
) :
    MvpPresenter<IUserView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun openForks(forkUrl: String) {
        router.navigateTo(Screens.ForksScreen(forkUrl))
    }

    fun getRepos(user: GithubUser) {
        user?.let {
            userRepo.getRepos(it)
                .observeOn(mainThreadScheduler)
                .subscribe({
                    viewState.setRepos(it)
                }, { print("Error: ${it.message}") })
        }

    }

}