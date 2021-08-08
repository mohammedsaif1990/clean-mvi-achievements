package com.kordia.achievements.presentation.activity.fragment.achievements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.presentation.databinding.ItemAchievementBinding

/**
 * AchievementsAdapter class.
 *
 * @author Mohammedsaif Kordia
 */
class AchievementsAdapter(private val achievementListener: AchievementListener) :
    RecyclerView.Adapter<AchievementViewHolder>() {

    /**
     * Achievements list.
     */
    private var list = listOf<AchievementDto>()

    /**
     * This function updates the adapter list and notifies the data set changes.
     *
     * @param list a list of achievements
     */
    fun updateData(list: List<AchievementDto>) {

        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder =

        AchievementViewHolder(
            ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {

        val achievement = list[position]
        holder.bindView(achievement, achievementListener)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = 0
}