package com.kordia.achievements.app.di

import com.kordia.achievements.data.api.achievement.AchievementApi
import com.kordia.achievements.data.api.goal.GoalApi
import com.kordia.achievements.data.api.type.TypeApi
import com.kordia.achievements.data.api.user.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * ApiModule is to provide di for retrofit interfaces
 *
 * @author Mohammedsaif Kordia
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideAchievementApi(retrofit: Retrofit.Builder): AchievementApi =
        retrofit.build().create(AchievementApi::class.java)

    @Provides
    fun provideGoalApi(retrofit: Retrofit.Builder): GoalApi =
        retrofit.build().create(GoalApi::class.java)

    @Provides
    fun provideTypeApiApi(retrofit: Retrofit.Builder): TypeApi =
        retrofit.build().create(TypeApi::class.java)

    @Provides
    fun provideUserApi(retrofit: Retrofit.Builder): UserApi =
        retrofit.build().create(UserApi::class.java)
}