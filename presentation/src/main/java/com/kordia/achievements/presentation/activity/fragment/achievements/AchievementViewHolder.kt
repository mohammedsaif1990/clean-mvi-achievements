package com.kordia.achievements.presentation.activity.fragment.achievements

import androidx.recyclerview.widget.RecyclerView
import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.presentation.databinding.ItemAchievementBinding

/**
 * AchievementViewHolder class.
 *
 * @author Mohammedsaif Kordia
 */
class AchievementViewHolder(private val binding: ItemAchievementBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(achievement: AchievementDto, achievementListener: AchievementListener) {

        binding.achievement = achievement
        binding.listener = achievementListener
        binding.executePendingBindings()
    }
}