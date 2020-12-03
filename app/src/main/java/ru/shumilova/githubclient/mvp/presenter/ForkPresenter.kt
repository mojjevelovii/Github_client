package ru.shumilova.githubclient.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.model.repository.IGithubUsersRepo
import ru.shumilova.githubclient.mvp.view.IForksView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ForkPresenter(
    private val mainThreadScheduler: Scheduler
) : MvpPresenter<IForksView>() {
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

    fun getRepos(repo: UserRepo) {
        userRepo.getForks(repo)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.setRepos(it)
            }, { print("Error: ${it.message}") })

    }
}