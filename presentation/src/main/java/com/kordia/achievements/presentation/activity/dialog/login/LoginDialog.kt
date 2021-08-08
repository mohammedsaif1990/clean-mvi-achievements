package com.kordia.achievements.presentation.activity.dialog.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.kordia.achievements.domain.model.AuthenticateRequestDto
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.activity.UserViewModel
import com.kordia.achievements.presentation.appstructure.BaseDialogFragment
import com.kordia.achievements.presentation.databinding.DialogLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * LoginDialog class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class LoginDialog : BaseDialogFragment<DialogLoginBinding>(),
    View.OnClickListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogLoginBinding
        get() = DialogLoginBinding::inflate

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * User view model that holds the current user info and controls the user operations.
     */
    private val userVM: UserViewModel by activityViewModels()

    /**
     * pre setup the the fragment and data binding
     */
    override fun setup() = with(binding) {

        listener = this@LoginDialog
        observeLiveData()
        isCancelable = false
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        userVM._loginLiveData = MutableLiveData<DataState<UserDto>>()
        userVM.loginLiveData.observe(viewLifecycleOwner) {
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
                            "Login error, please try again!",
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
            binding.btLogin ->
                userVM.login(
                    AuthenticateRequestDto(
                        username = binding.edUsername.text.toString(),
                        password = binding.edPassword.text.toString(),
                        rememberMe = true
                    )
                )
            binding.ivClose -> dismiss()
        }
    }
}