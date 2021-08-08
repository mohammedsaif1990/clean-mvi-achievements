package com.kordia.achievements.app.di

import com.kordia.achievements.data.db.AchievementsDatabase
import com.kordia.achievements.data.db.achievement.AchievementDao
import com.kordia.achievements.data.db.goal.GoalDao
import com.kordia.achievements.data.db.setting.SettingDao
import com.kordia.achievements.data.db.type.TypeDao
import com.kordia.achievements.data.db.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * DaoModule is to provide di for Room DAOs interfaces
 *
 * @author Mohammedsaif Kordia
 */
@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideAchievementDao(achievementsDatabase: AchievementsDatabase): AchievementDao =
        achievementsDatabase.achievementDao()

    @Provides
    fun provideGoalDao(achievementsDatabase: AchievementsDatabase): GoalDao =
        achievementsDatabase.goalDao()

    @Provides
    fun provideSetting(achievementsDatabase: AchievementsDatabase): SettingDao =
        achievementsDatabase.settingDao()

    @Provides
    fun provideTypeDao(achievementsDatabase: AchievementsDatabase): TypeDao =
        achievementsDatabase.typeDao()

    @Provides
    fun provideUserDao(achievementsDatabase: AchievementsDatabase): UserDao =
        achievementsDatabase.userDao()
}