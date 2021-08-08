package com.kordia.achievements.presentation.activity.dialog.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.activity.UserViewModel
import com.kordia.achievements.presentation.appstructure.BaseDialogFragment
import com.kordia.achievements.presentation.databinding.DialogAccountBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * AccountDialog class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class AccountDialog : BaseDialogFragment<DialogAccountBinding>(), View.OnClickListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAccountBinding
        get() = DialogAccountBinding::inflate

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * User view model that holds the current user info and controls the user operations.
     */
    private val userVM: UserViewModel by activityViewModels()

    /**
     * pre setup the the fragment and data binding.
     */
    override fun setup() = with(binding) {

        listener = this@AccountDialog
        observeLiveData()
        isCancelable = false
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        userVM._userLiveData.observe(viewLifecycleOwner) {
            it?.let { dataState ->
                if (dataState is DataState.Success) {
                    binding.user = dataState.data
                }
            }
        }

        userVM._logoutLiveData = MutableLiveData<DataState<UserDto>>()
        userVM.logoutLiveData.observe(viewLifecycleOwner) {
            it?.let { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        userVM._userLiveData.postValue(dataState)
                        mainActivityVM.showLoading(false)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${dataState.data.login}",
                            Toast.LENGTH_LONG
                        ).show()
                        dismiss()
                    }
                    is DataState.Error -> {
                        dataState.exception.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            "Logout error, please try again!",
                            Toast.LENGTH_LONG
                        ).show()
                        mainActivityVM.showLoading(false)
                        dismiss()
                    }
                    is DataState.Loading -> {
                        mainActivityVM.showLoading(true)
                    }
                    is DataState.Empty -> {
                        mainActivityVM.showLoading(false)
                    }
                }
            }
        }
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

        when (view) {
            binding.btLogout -> userVM.logOut()
            binding.ivClose -> dismiss()
        }
    }
}