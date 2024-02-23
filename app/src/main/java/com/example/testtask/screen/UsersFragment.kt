package com.example.testtask.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.testtask.data.ViewModelData
import com.example.testtask.data.db.User
import com.example.testtask.data.db.UserViewModel
import com.example.testtask.data.rv.OnItemClickListener
import com.example.testtask.data.rv.UserAdapter
import com.example.testtask.databinding.FragmentUsersBinding
import com.example.testtask.mainActivity
import kotlinx.coroutines.launch
import com.example.testtask.navigation.App
import com.example.testtask.navigation.UserNavViewModel
import com.example.testtask.ui.Screens


class UsersFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private var adapter: UserAdapter? = null
    private val viewModel = UserNavViewModel(App.INSTANCE.userRouter)
    private val viewModelData: ViewModelData by activityViewModels()
    private lateinit var userViewModel: UserViewModel
    private var refresh = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tbUsers.setNavigationOnClickListener {
            refresh = true
            userViewModel.deleteAllUsers()

        }


        launchUsersLoading()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    private fun launchUsersLoading() {
        userViewModel.readAllData.observe(viewLifecycleOwner) {users ->
            showProgress()
            if (users.isEmpty() && !refresh) {
                loadUsers()
            } else if (refresh) {
                loadUsers()
                refresh = false
            }
            else {
                showContent(users)
                refresh = false
            }


        }


    }


    private fun showContent(users: List<User>) = with(binding) {
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

    private fun loadUsers() {
        lifecycleScope.launch {
            try {
                val repository = mainActivity.repository
                val usersResponse = repository.getUsers().results
                val usersList = mutableListOf<User>()

                usersResponse.forEach{
                    val user = User(
                        0,
                        it.name?.first,
                        it.name?.last,
                        it.picture?.large,
                        it.dob?.date,
                        it.gender,
                        it.dob?.age.toString(),
                        it.location?.country,
                        it.location?.state,
                        it.location?.city,
                        "${it.location?.street?.number} ${it.location?.street?.name}",
                        it.location?.timezone?.offset,
                        it.location?.postcode.toString(),
                        it.email,
                        it.phone,
                        it.cell,
                        "${it.location?.coordinates?.latitude}, ${it.location?.coordinates?.longitude}"
                    )
                    usersList.add(user)
                }

                userViewModel.addUsers(usersList)
                showContent(usersList)
            } catch (ex: Exception) {
                showError(ex.message.orEmpty())
            }
        }
    }
    override fun onItemClick(position: Int) {
        viewModel.navigateTo(Screens.infoUserScreen())
        viewModelData.infoUser.value = adapter?.getUser(position)
    }


}