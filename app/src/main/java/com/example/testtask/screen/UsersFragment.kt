package com.example.testtask.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.testtask.data.rv.OnItemClickListener
import com.example.testtask.data.rv.UserAdapter
import com.example.testtask.databinding.FragmentUsersBinding
import com.example.testtask.mainActivity
import kotlinx.coroutines.launch
import com.example.testtask.data.user_model.Result


class UsersFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private var adapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchUsersLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    private fun launchUsersLoading() {
        showProgress()

        lifecycleScope.launch{
            try {
                val repository = mainActivity.repository
                val users = repository.getUsers().results
                showContent(users)
            } catch (ex: Exception) {
                showError(ex.message.orEmpty())
            }
        }
    }

    private fun showContent(users: List<Result>) = with(binding) {
        pbUsersContent.isVisible = false
        contentError.isVisible = false

        rvUsers.isVisible = true
        adapter = UserAdapter(this@UsersFragment)
        adapter?.addUsers(users)

        rvUsers.adapter = adapter
    }

    private fun showProgress() = with(binding) {
        rvUsers.isVisible = false
        contentError.isVisible = false

        pbUsersContent.isVisible = true
    }

    private fun showError(message: String) = with(binding) {
        pbUsersContent.isVisible = false
        rvUsers.isVisible = false

        contentError.isVisible = true
        tvError.text = message
        btnRefresh.setOnClickListener { launchUsersLoading() }
    }

    override fun onItemClick(position: Int) {

    }

}