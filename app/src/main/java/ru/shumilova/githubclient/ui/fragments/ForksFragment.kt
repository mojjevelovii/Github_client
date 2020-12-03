package ru.shumilova.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_forks.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.model.entity.ForksResponse
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.model.repository.GithubUsersRepo
import ru.shumilova.githubclient.mvp.presenter.ForkPresenter
import ru.shumilova.githubclient.mvp.view.IForksView
import ru.shumilova.githubclient.ui.BackButtonListener
import ru.shumilova.githubclient.ui.adapter.ReposRVAdapter

class ForksFragment : MvpAppCompatFragment(), IForksView, BackButtonListener {
    private var adapter: ReposRVAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val forkPresenter: ForkPresenter by moxyPresenter {
        ForkPresenter(
            AndroidSchedulers.mainThread()
        ).apply { GithubApplication.application.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_forks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userRepo = arguments?.getParcelable<UserRepo>(KEY)
        init()
        userRepo?.let { forkPresenter.getRepos(it) }
    }

    private fun init() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = ReposRVAdapter { url -> forkPresenter.openForks(url) }
        rv_forks.layoutManager = layoutManager
        rv_forks.adapter = adapter
    }



    override fun setRepos(repList: ForksResponse) {
        adapter?.data = repList.forks
        tv_forks_quantity.text = repList.forksCount.toString()
    }

    override fun backPressed(): Boolean {
        return forkPresenter.backPressed()
    }

    companion object {
        private const val KEY = "key"

        fun newInstance(repo: UserRepo) =
            ForksFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(KEY, repo)
                arguments = bundle
            }
    }
}