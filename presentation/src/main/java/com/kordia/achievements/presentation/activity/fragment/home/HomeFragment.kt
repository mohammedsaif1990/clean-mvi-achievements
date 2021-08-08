package com.kordia.achievements.presentation.activity.fragment.home

import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kordia.achievements.presentation.R
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.activity.UserViewModel
import com.kordia.achievements.presentation.appstructure.BaseFragment
import com.kordia.achievements.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * HomeFragment class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val homeVM: HomeViewModel by viewModels()

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
        lifecycleOwner = viewLifecycleOwner
        viewModel = homeVM
        listener = this@HomeFragment
        registerForContextMenu(binding.fab)
        setHasOptionsMenu(true)
        observeLiveData()
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        homeVM.errorLiveData.observe(viewLifecycleOwner) {
            it?.let { Timber.tag("err").d(it.cause) }
        }

        homeVM.loadingLiveData.observe(viewLifecycleOwner) {
            it?.let { mainActivityVM.showLoading(it) }
        }

        homeVM.messageLiveData.observe(viewLifecycleOwner) {
            it?.let { makeText(binding.root, it) }
        }

        userVM.userLiveData.observe(viewLifecycleOwner) {
            it?.let { homeVM.onTriggerEvent(HomeIntent.GetHomStatisticsEvent) }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_achievement ->
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalToAchievementAddFragment("-1")
                )
            R.id.mi_goal ->
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalToGoalAddFragment("-1")
                )
        }
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        view: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, view, menuInfo)
        when (view) {
            binding.fab ->
                hostActivity?.let {
                    val inflater: MenuInflater = it.menuInflater
                    inflater.inflate(R.menu.menu_add_from_home, menu)
                }

        }
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {
        when (view) {
            binding.fab -> binding.fab.performLongClick()
            binding.tvGoalsWaiting -> findNavController().navigate(HomeFragmentDirections.actionGlobalToGoalsFragment())
        }
    }
}