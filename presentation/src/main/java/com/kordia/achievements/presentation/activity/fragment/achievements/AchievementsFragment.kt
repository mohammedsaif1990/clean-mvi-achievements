package com.kordia.achievements.presentation.activity.fragment.achievements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.activity.UserViewModel
import com.kordia.achievements.presentation.activity.fragment.achievements.add.AchievementAddFragmentDirections
import com.kordia.achievements.presentation.appstructure.BaseFragment
import com.kordia.achievements.presentation.databinding.FragmentAchievementsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * AchievementsFragment class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class AchievementsFragment : BaseFragment<FragmentAchievementsBinding>(),
    View.OnClickListener, AchievementListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAchievementsBinding
        get() = FragmentAchievementsBinding::inflate

    private val achievementsVM: AchievementsViewModel by viewModels()

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * User view model that holds the current user info and controls the user operations.
     */
    private val userVM: UserViewModel by activityViewModels()

    private val adapter by lazy { AchievementsAdapter(this) }

    /**
     * pre setup the the fragment and data binding.
     */
    override fun setup() = with(binding) {

        observeLiveData()
        listener = this@AchievementsFragment
        rvAchievements.adapter = adapter
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        achievementsVM.achievementsLiveData.observe(viewLifecycleOwner) {
            it?.let { achievements -> adapter.updateData(achievements) }
        }

        achievementsVM.errorLiveData.observe(viewLifecycleOwner) {
            it?.let { Timber.tag("err").d(it.cause) }
        }

        achievementsVM.loadingLiveData.observe(viewLifecycleOwner) {
            it?.let { mainActivityVM.showLoading(it) }
        }

        achievementsVM.messageLiveData.observe(viewLifecycleOwner) {
            it?.let { makeText(binding.root, it) }
        }

        achievementsVM.text.observe(viewLifecycleOwner) {
            it?.let { binding.textGallery.text = it }
        }

        userVM.userLiveData.observe(viewLifecycleOwner) {
            it?.let { achievementsVM.onTriggerEvent(AchievementsIntent.GetAchievementsEvent) }
        }
    }

    override fun onAchievementClick(id: String) {

        findNavController().navigate(
            AchievementAddFragmentDirections.actionGlobalToAchievementAddFragment(id)
        )
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

        when (view) {
            binding.fab -> findNavController().navigate(AchievementAddFragmentDirections.actionGlobalToAchievementAddFragment())
        }
    }
}