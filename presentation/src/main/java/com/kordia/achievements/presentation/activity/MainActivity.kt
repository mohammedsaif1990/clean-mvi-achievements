package com.kordia.achievements.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.kordia.achievements.domain.Constants.GUEST_USER_ID
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.R
import com.kordia.achievements.presentation.activity.dialog.account.AccountDialog
import com.kordia.achievements.presentation.activity.dialog.login.LoginDialog
import com.kordia.achievements.presentation.databinding.ActivityMainBinding
import com.kordia.achievements.presentation.databinding.NavHeaderMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityMainBinding

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by viewModels()

    /**
     * User view model that holds the current user info and controls the user operations.
     */
    private val userVM: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_homeFragment, R.id.nav_achievementsFragment, R.id.nav_goalsFragment),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        observeLiveData()
        userVM.initUser()
        binding.appBarMain.main.liLoading
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.appBarMain.main.liLoading.visibility = View.VISIBLE
        else binding.appBarMain.main.liLoading.visibility = View.GONE
    }

    private fun setNavInfo(user: UserDto) {
        val headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        headerBinding.tvName.text = user.firstName
        headerBinding.tvEmail.text = user.email
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {
        userVM.userLiveData.observe(this) { dataState ->
            dataState?.let {
                when (dataState) {
                    is DataState.Empty -> {
                    }
                    is DataState.Error -> {
                        dataState.exception.printStackTrace()
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        val user = dataState.data
                        userVM.isGuestUser = user.id == GUEST_USER_ID
                        setNavInfo(user)
                    }
                }
            }
        }

        mainActivityVM.loadingLiveData.observe(this) {
            it?.let { showLoading(it) }
        }
    }

    private fun makeText(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", this).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_account -> {
                if (userVM.isGuestUser) {
                    val dialog = LoginDialog()
                    dialog.show(supportFragmentManager, "login")
                } else {
                    val dialog = AccountDialog()
                    dialog.show(supportFragmentManager, "account")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

    }
}