package com.example.testtask.screen

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer


class UsersFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private var adapter: UserAdapter? = null
    private val viewModel = UserNavViewModel(App.INSTANCE.userRouter)
    private val viewModelData: ViewModelData by activityViewModels()
    private lateinit var userViewModel: UserViewModel
    private val server = MockWebServer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMockServer()
    }


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

        adapter = UserAdapter(this@UsersFragment)

        binding.tbUsers.setNavigationOnClickListener {
            userViewModel.deleteAllUsers()
            adapter?.clearAll()

        }

        userViewModel.readAllData.observe(viewLifecycleOwner) { users ->
            adapter?.addUsers(users)

            launchUsersLoading()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
        server.shutdown()
    }

    private fun launchUsersLoading() {
        showProgress()


        if (adapter?.getSize() == 0) {
            loadUsers()
        } else {
            showContent()
        }
    }


    private fun showContent() = with(binding) {
        pbUsersContent.isVisible = false
        contentError.isVisible = false

        rvUsers.isVisible = true

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
        loadDataMockServer()

//        lifecycleScope.launch {
//            try {
//
//                val repository = mainActivity.repository
//                val usersResponse = repository.getUsers().results
//                val usersList = mutableListOf<User>()
//
//                usersResponse.forEach{
//                    val user = User(
//                        0,
//                        it.name?.first,
//                        it.name?.last,
//                        it.picture?.large,
//                        it.dob?.date,
//                        it.gender,
//                        it.dob?.age.toString(),
//                        it.location?.country,
//                        it.location?.state,
//                        it.location?.city,
//                        "${it.location?.street?.number} ${it.location?.street?.name}",
//                        it.location?.timezone?.offset,
//                        it.location?.postcode.toString(),
//                        it.email,
//                        it.phone,
//                        it.cell,
//                        "${it.location?.coordinates?.latitude}, ${it.location?.coordinates?.longitude}"
//                    )
//
//                    usersList.add(user)
//                }
//
//                userViewModel.addUsers(usersList)
//                showContent()
//            } catch (ex: Exception) {
//                showError(ex.message.orEmpty())
//                Log.i("MyLog", ex.message.toString())
//            }
//        }
    }


    override fun onItemClick(position: Int) {
        viewModel.navigateTo(Screens.infoUserScreen())
        viewModelData.infoUser.value = adapter?.getUser(position)
    }



    private fun loadDataMockServer() {
        lifecycleScope.launch(Dispatchers.IO) {
            enqueueFakeData()

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(server.url("/"))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val gson = Gson()
            val userResponseList = gson.fromJson(responseBody, UserResponse::class.java).users

            val userList = mutableListOf<User>()

            userResponseList.forEach {
                val user = User(
                    0,
                    it.name,
                    "",
                    "https://randomuser.me/api/portraits/thumb/men/16.jpg",
                    "1958-03-28T16:59:13.071Z",
                    "cat",
                    "66",
                    "",
                    "",
                    "",
                    it.address,
                    "",
                    "",
                    "",
                    it.phoneNumber,
                    "",
                    ""
                )

                userList.add(user)
            }


            requireActivity().runOnUiThread {
                userViewModel.addUsers(userList)
                showContent()
            }


        }
    }


    private fun startMockServer() {
        lifecycleScope.launch(Dispatchers.IO) {
            server.start()
        }
    }

    private fun enqueueFakeData() {
        val fakeResponse = MockResponse()
            .setResponseCode(200)
            .setBody(fakeServerResponse) // Здесь fakeServerResponse - это ваш фэйковый ответ с данными
        server.enqueue(fakeResponse)
    }



    companion object {
        private const val fakeServerResponse = """
{
  "users": [
    {
      "name": "Ryan Gosling",
      "address": "123 Main St",
      "phoneNumber": "555-1234"
    },
    {
      "name": "Andrey Shtrich",
      "address": "456 Elm St",
      "phoneNumber": "555-5678"
    },
    {
      "name": "Kostya Tubic",
      "address": "789 Oak St",
      "phoneNumber": "555-9012"
    },
    {
      "name": "Oleg Skuff",
      "address": "1 Usova",
      "phoneNumber": "555-9012"
    },
    {
      "name": "Ann Altushka",
      "address": "789 Oak St",
      "phoneNumber": "555-9012"
    }
  ]
}
"""
    }

    data class UserResponse(val users: List<UserMock>)
    data class UserMock(val name: String, val address: String, val phoneNumber: String)

}