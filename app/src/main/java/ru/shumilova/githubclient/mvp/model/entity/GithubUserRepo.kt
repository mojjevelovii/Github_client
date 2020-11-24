package ru.shumilova.githubclient.mvp.model.entity

import io.reactivex.rxjava3.core.Single

class GithubUserRepo {
    private val repositories: Single<List<GithubUser>> =
        Single.just(
            listOf(
                GithubUser("login1"),
                GithubUser("login2"),
                GithubUser("login3"),
                GithubUser("login4"),
                GithubUser("login5")
            )
        )

    val users: Single<List<GithubUser>>
        get() = repositories
}