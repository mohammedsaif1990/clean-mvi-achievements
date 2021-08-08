package com.kordia.achievements.presentation.activity.fragment.goals.add

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
import com.kordia.achievements.presentation.databinding.FragmentGoalAddBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

/**
 * GoalAddFragment class.
 *
 * @author Mohammedsaif Kordia
 */
@AndroidEntryPoint
class GoalAddFragment : BaseFragment<FragmentGoalAddBinding>(), View.OnClickListener {

    /**
     * inflates the data binding.
     */
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoalAddBinding
        get() = FragmentGoalAddBinding::inflate

    private val goalAddVM: GoalAddViewModel by viewModels()
    private val mainActivityVM: MainActivityViewModel by activityViewModels()

    /**
     * pre setup the the fragment and data binding.
     */
    override fun setup() = with(binding) {

        lifecycleOwner = viewLifecycleOwner
        viewModel = goalAddVM
        createEvent = GoalAddIntent.CreateEvent
        updateEvent = GoalAddIntent.UpdateEvent
        setAchievedEvent = GoalAddIntent.SetAchievedEvent
        listener = this@GoalAddFragment
        observeLiveData()
        goalAddVM.goalId =
            arguments?.let { GoalAddFragmentArgs.fromBundle(it).goalId } ?: "-1"
        if (goalAddVM.goalId != "-1") {
            goalAddVM.onTriggerEvent(GoalAddIntent.GetEvent)
        }
    }

    /**
     * Observe all view models live data.
     */
    private fun observeLiveData() {

        goalAddVM.errorLiveData.observe(viewLifecycleOwner) {
            it?.let { Timber.tag("err").d(it.cause) }
        }

        goalAddVM.eventStateLiveData.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    EventState.Back -> findNavController().popBackStack()
                }
            }
        }

        goalAddVM.loadingLiveData.observe(viewLifecycleOwner) {
            it?.let { mainActivityVM.showLoading(it) }
        }

        goalAddVM.messageLiveData.observe(viewLifecycleOwner) {
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
            .setMessage("Delete This Goal?")
            .setPositiveButton("Delete") { _, _ ->
                goalAddVM.onTriggerEvent(GoalAddIntent.DeleteEvent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
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
                goalAddVM.onTriggerEvent(GoalAddIntent.SetDateEvent(calendar.timeInMillis))
            },
            nowCalendar.get(Calendar.YEAR),
            nowCalendar.get(Calendar.MONTH),
            nowCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    /**
     * On click listener.
     */
    override fun onClick(view: View?) {

        when (view) {
            binding.tvEstimatedDateValue -> openDatePickerDialog()
            binding.btDelete -> confirmDelete()
        }
    }
}