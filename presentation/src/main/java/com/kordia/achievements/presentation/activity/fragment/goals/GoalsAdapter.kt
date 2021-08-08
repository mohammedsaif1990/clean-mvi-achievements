package com.kordia.achievements.presentation.activity.fragment.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.presentation.databinding.ItemGoalBinding

/**
 * GoalsAdapter class.
 *
 * @author Mohammedsaif Kordia
 */
class GoalsAdapter(private val goalListener: GoalListener) :
    RecyclerView.Adapter<GoalViewHolder>() {

    /**
     * Goals list.
     */
    private var list = listOf<GoalDto>()

    /**
     * This function updates the adapter list and notifies the data set changes.
     *
     * @param list a list of goals
     */
    fun updateData(list: List<GoalDto>) {

        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder =

        GoalViewHolder(
            ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = list[position]
        holder.bindView(goal, goalListener)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = 0
}