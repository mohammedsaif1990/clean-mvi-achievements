package com.kordia.achievements.presentation.activity.fragment.achievements.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kordia.achievements.domain.utils.EventState
import com.kordia.achievements.presentation.activity.MainActivityViewModel
import com.kordia.achievements.presentation.appstructure.BaseFragment
import com.kordia.achievements.presentation.databinding.FragmentAchievementAddBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

/**
 * AchievementAddFragment class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class AchievementAddFragment : BaseFragment<FragmentAchievementAddBinding>(), View.OnClickListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAchievementAddBinding
        get() = FragmentAchievementAddBinding::inflate

    private val achievementAddVM: AchievementAddViewModel by viewModels()

    /**
     * Main activity view model is responsible of all the activity scooped operations.
     */
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * pre setup the the fragment and data binding.
     */
    override fun setup() = with(binding) {

        lifecycleOwner = viewLifecycleOwner
        viewModel = achievementAddVM
        createEvent = AchievementAddIntent.CreateEvent
        updateEvent = AchievementAddIntent.UpdateEvent
        listener = this@AchievementAddFragment
        observeLiveData()
        achievementAddVM.achievementId =
            arguments?.let { AchievementAddFragmentArgs.fromBundle(it).achievementId } ?: "-1"
        if (achievementAddVM.achievementId != "-1") {
            achievementAddVM.onTriggerEvent(AchievementAddIntent.GetEvent)
        }
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        achievementAddVM.errorLiveData.observe(viewLifecycleOwner) {
            it?.let { Timber.tag("err").d(it.cause) }
        }

        achievementAddVM.eventStateLiveData.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    EventState.Back -> findNavController().popBackStack()
                }
            }
        }

        achievementAddVM.loadingLiveData.observe(viewLifecycleOwner) {
            it?.let { mainActivityVM.showLoading(it) }
        }

        achievementAddVM.messageLiveData.observe(viewLifecycleOwner) {
            it?.let { makeText(binding.root, it) }
        }
    }

    /**
     * Shows a confirmation dialog before the delete.
     */
    private fun confirmDelete() {

        AlertDialog
            .Builder(requireContext())
            .setTitle("Delete")
            .setMessage("Delete this Achievement?")
            .setPositiveButton("Delete") { _, _ ->
                achievementAddVM.onTriggerEvent(AchievementAddIntent.DeleteEvent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

        when (view) {
            binding.tvDateValue -> openDatePickerDialog()
            binding.btDelete -> confirmDelete()
        }
    }

    /**
     * Shows a date picker dialog.
     */
    private fun openDatePickerDialog() {

        val nowCalendar: Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val calendar: Calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                achievementAddVM.onTriggerEvent(AchievementAddIntent.SetDateEvent(calendar.timeInMillis))
            },
            nowCalendar.get(Calendar.YEAR),
            nowCalendar.get(Calendar.MONTH),
            nowCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}