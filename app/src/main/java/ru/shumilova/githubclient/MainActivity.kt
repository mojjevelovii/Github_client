package ru.shumilova.githubclient

import android.os.Bundle
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.shumilova.githubclient.mvp.presenter.MainPresenter
import ru.shumilova.githubclient.mvp.view.IMainView
import ru.shumilova.githubclient.ui.BackButtonListener
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), IMainView {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val presenter: MainPresenter by moxyPresenter {
        MainPresenter().apply {
            GithubApplication.application.appComponent.inject(this)
        }
    }
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GithubApplication.application.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
            presenter.backClicked()
        }
    }
}