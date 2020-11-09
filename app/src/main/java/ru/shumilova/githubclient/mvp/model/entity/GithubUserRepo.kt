package ru.shumilova.githubclient.mvp.model.entity

import java.util.*

class GithubUserRepo {
    private val repositories: List<GithubUser> =
        listOf(
            GithubUser("login1"),
            GithubUser("login2"),
            GithubUser("login3"),
            GithubUser("login4"),
            GithubUser("login5")
        )


    val users: List<GithubUser>
        get() = Collections.unmodifiableList(repositories)
}