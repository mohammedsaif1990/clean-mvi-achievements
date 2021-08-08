package com.kordia.achievements.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.AuthenticateRequestDto
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.repository.UserRepository
import com.kordia.achievements.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UserViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
@Suppress("PropertyName")
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var _loginLiveData = MutableLiveData<DataState<UserDto>>()
    val loginLiveData: LiveData<DataState<UserDto>> get() = _loginLiveData

    var _logoutLiveData = MutableLiveData<DataState<UserDto>>()
    val logoutLiveData: LiveData<DataState<UserDto>> get() = _logoutLiveData

    val _userLiveData = MutableLiveData<DataState<UserDto>>()
    val userLiveData: LiveData<DataState<UserDto>> get() = _userLiveData

    var isGuestUser = true

    /**
     * Initiates the user.
     */
    fun initUser() =

        viewModelScope.launch {
            userRepository.initUser()
                .map { _userLiveData.postValue(it) }
                .launchIn(viewModelScope)
        }

    /**
     * Login this function will authorize the user online and will set it locally
     * if authorized successfully.
     *
     * @param authenticateRequest that contains the login credentials
     */
    fun login(authenticateRequest: AuthenticateRequestDto) =

        viewModelScope.launch {
            userRepository.login(authenticateRequest)
                .map { _loginLiveData.postValue(it) }
                .launchIn(viewModelScope)
        }

    /**
     * Logout the current user.
     */
    fun logOut() =

        viewModelScope.launch {
            userRepository.logout()
                .map { _logoutLiveData.postValue(it) }
                .launchIn(viewModelScope)
        }
}