package com.example.testtask.screen

import android.annotation.SuppressLint
import android.icu.text.IDNA.Info
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.data.ViewModelData
import com.example.testtask.data.db.User
import com.example.testtask.data.rv.InfoAdapter
import com.example.testtask.data.rv.InfoModel
import com.example.testtask.databinding.FragmentInfoUserBinding
import com.example.testtask.navigation.App
import com.example.testtask.navigation.UserNavViewModel
import java.text.SimpleDateFormat
import com.example.testtask.data.user_model.Result

class InfoUserFragment : Fragment() {
    private var _binding: FragmentInfoUserBinding? = null
    private val binding get() = _binding!!
    private var adapter: InfoAdapter? = null
    private val viewModel = UserNavViewModel(App.INSTANCE.userRouter)
    private val viewModelData: ViewModelData by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInfoUserBinding.inflate(inflater, container, false)
        adapter = InfoAdapter()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tbInfoUser.setNavigationOnClickListener {
                viewModel.backTo()
            }

            viewModelData.infoUser.observe(activity as LifecycleOwner) {user ->
                if (isAdded && activity != null) {
                    user.apply {
                        Glide.with(this@InfoUserFragment)
                            .load(picture)
                            .into(imgInfoProfile)
                        tvInfoName.text = String.format(getString(R.string.template_name), firstName, lastName)
                        tvDateBirthday.text = birthday?.let { dateFormat(it) }
                        tvSex.text = sex
                    }

                    addInfo(user)
                    binding.rvInformation.adapter = adapter
                }

            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = inputFormat.parse(dateTimeString)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")

        return outputFormat.format(date)
    }

    private fun addInfo(information: User) {
        adapter?.apply {
            addInfo(InfoModel(getString(R.string.age), information.age!!))
            addInfo(InfoModel(getString(R.string.country), information.country!!))
            addInfo(InfoModel(getString(R.string.state), information.state!!))
            addInfo(InfoModel(getString(R.string.city), information.city!!))
            addInfo(InfoModel(getString(R.string.address), information.address!!))
            addInfo(InfoModel(getString(R.string.postcode), information.postcode!!))
            addInfo(InfoModel(getString(R.string.timezone), information.timezone!!))
            addInfo(InfoModel(getString(R.string.email), information.email!!))
            addInfo(InfoModel(getString(R.string.phone), information.phone!!))
            addInfo(InfoModel(getString(R.string.cell), information.cell!!))
            addInfo(InfoModel(getString(R.string.location), information.location!!))
        }
    }



}