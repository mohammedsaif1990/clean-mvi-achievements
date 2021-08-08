package com.kordia.achievements.presentation.activity.fragment.goals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.activity.UserViewModel
import com.kordia.achievements.presentation.appstructure.BaseFragment
import com.kordia.achievements.presentation.databinding.FragmentGoalsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * GoalsFragment class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class GoalsFragment : BaseFragment<FragmentGoalsBinding>(), View.OnClickListener, GoalListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoalsBinding
        get() = FragmentGoalsBinding::inflate

    private val goalsVM: GoalsViewModel by viewModels()

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * User view model that holds the current user info and controls the user operations.
     */
    private val userVM: UserViewModel by activityViewModels()

    private val adapter by lazy { GoalsAdapter(this) }

    /**
     * pre setup the the fragment and data binding.
     */
    override fun setup() = with(binding) {

        observeLiveData()
        rvGoals.adapter = adapter
        binding.fab.setOnClickListener(this@GoalsFragment)
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        goalsVM.errorLiveData.observe(viewLifecycleOwner) {
            it?.let { Timber.tag("err").d(it.cause) }
        }

        goalsVM.goalsLiveData.observe(viewLifecycleOwner) {
            it.let { goals ->
                adapter.updateData(goals)
            }
        }

        goalsVM.loadingLiveData.observe(viewLifecycleOwner) {
            it?.let { mainActivityVM.showLoading(it) }
        }

        goalsVM.messageLiveData.observe(viewLifecycleOwner) {
            it?.let { makeText(binding.root, it) }
        }

        goalsVM.text.observe(viewLifecycleOwner) {
            it?.let { binding.textSlideshow.text = it }
        }

        userVM.userLiveData.observe(viewLifecycleOwner) {
            it?.let {
                goalsVM.onTriggerEvent(GoalsIntent.GetGoalsEvent)
            }
        }
    }

    override fun onGoalClick(id: String) {

        findNavController().navigate(
            GoalsFragmentDirections.actionGlobalToGoalAddFragment(id)
        )
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

        when (view) {
            binding.fab -> findNavController().navigate(GoalsFragmentDirections.actionGlobalToGoalAddFragment())
        }
    }
}