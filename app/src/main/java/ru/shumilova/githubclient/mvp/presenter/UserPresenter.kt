package ru.shumilova.githubclient.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.model.repository.IGithubUsersRepo
import ru.shumilova.githubclient.mvp.view.IUserView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UserPresenter(
    private val mainThreadScheduler: Scheduler
) :
    MvpPresenter<IUserView>() {
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var userRepo: IGithubUsersRepo

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun openForks(repo: UserRepo) {
        router.navigateTo(Screens.ForksScreen(repo))
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