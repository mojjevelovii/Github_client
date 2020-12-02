package ru.shumilova.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.R
import ru.shumilova.githubclient.mvp.presenter.UsersPresenter
import ru.shumilova.githubclient.mvp.view.IUsersView
import ru.shumilova.githubclient.mvp.view.image.GlideImageLoader
import ru.shumilova.githubclient.ui.BackButtonListener
import ru.shumilova.githubclient.ui.adapter.UserRVAdapter

class UsersFragment : MvpAppCompatFragment(), IUsersView, BackButtonListener {
    private var adapter: UserRVAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val usersPresenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread()
        ).apply { GithubApplication.application.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_users, container, false)

    override fun init() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = UserRVAdapter(usersPresenter.usersListPresenter, GlideImageLoader())
        rv_users.layoutManager = layoutManager
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed()=usersPresenter.backPressed()

    companion object {
        fun getInstance(data: Int): UsersFragment {
            val fragment = UsersFragment()
            val bundle = Bundle()
            bundle.putInt("key", data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
