package com.kordia.achievements.presentation.activity.fragment.goals

import androidx.recyclerview.widget.RecyclerView
import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.presentation.databinding.ItemGoalBinding

/**
 * GoalViewHolder class.
 *
 * @author Mohammedsaif Kordia
 */
class GoalViewHolder(private val binding: ItemGoalBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(goal: GoalDto, goalListener: GoalListener) {

        binding.goal = goal
        binding.listener = goalListener
        binding.executePendingBindings()
    }
}