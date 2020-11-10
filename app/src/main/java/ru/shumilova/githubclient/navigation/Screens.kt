package ru.shumilova.githubclient.navigation

import androidx.fragment.app.Fragment
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.ui.fragments.UserFragment
import ru.shumilova.githubclient.ui.fragments.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen


class Screens {
    class UsersScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? = UsersFragment.getInstance(0)
    }

    class UserScreen(private val user: GithubUser) : SupportAppScreen() {
        override fun getFragment(): Fragment? = UserFragment.getInstance(user)
    }
}
