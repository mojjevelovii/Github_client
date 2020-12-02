package ru.shumilova.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.presenter.UserPresenter
import ru.shumilova.githubclient.mvp.view.IUserView
import ru.shumilova.githubclient.ui.BackButtonListener
import ru.shumilova.githubclient.ui.adapter.ReposRVAdapter

class UserFragment : MvpAppCompatFragment(), IUserView, BackButtonListener {
    private var adapter: ReposRVAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val userPresenter: UserPresenter by moxyPresenter {
        UserPresenter(
            AndroidSchedulers.mainThread()
        ).apply { GithubApplication.application.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = arguments?.getParcelable<GithubUser>(KEY)
        tv_login.text = user?.login
        init()
        user?.let { userPresenter.getRepos(it) }
    }

    private fun init() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = ReposRVAdapter { url -> userPresenter.openForks(url) }
        rv_repos.layoutManager = layoutManager
        rv_repos.adapter = adapter
    }

    companion object {
        private const val KEY = "key"
        fun getInstance(user: GithubUser): UserFragment {
            val fragment = UserFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun backPressed() = userPresenter.backPressed()

    override fun setRepos(repList: List<UserRepo>) {
        adapter?.data = repList
    }
}