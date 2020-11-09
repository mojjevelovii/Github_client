package ru.shumilova.githubclient

import android.os.Bundle
import android.os.Handler
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import ru.shumilova.githubclient.mvp.presenter.MainPresenter
import ru.shumilova.githubclient.mvp.view.IMainView
import ru.shumilova.githubclient.ui.BackButtonListener
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), IMainView {

    private val presenter: MainPresenter by moxyPresenter { MainPresenter() }
    private lateinit var  navigatorHolder: NavigatorHolder

    private val navigator: Navigator =
        SupportAppNavigator(this, supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GithubApplication.application?.navigatorHolder?.let { navigatorHolder = it }
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
        super.onBackPressed()
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is BackButtonListener && (fragment as BackButtonListener).backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}