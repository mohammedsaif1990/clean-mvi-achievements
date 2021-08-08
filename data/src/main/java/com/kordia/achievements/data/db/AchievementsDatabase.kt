package com.kordia.achievements.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kordia.achievements.data.db.achievement.AchievementDao
import com.kordia.achievements.data.db.achievement.AchievementData
import com.kordia.achievements.data.db.goal.GoalDao
import com.kordia.achievements.data.db.goal.GoalData
import com.kordia.achievements.data.db.setting.SettingDao
import com.kordia.achievements.data.db.setting.SettingData
import com.kordia.achievements.data.db.type.TypeDao
import com.kordia.achievements.data.db.type.TypeData
import com.kordia.achievements.data.db.user.UserDao
import com.kordia.achievements.data.db.user.UserData

/**
 * Room database class.
 *
 * @author Mohammedsaif Kordia
 */
@Database(
    entities = [
        AchievementData::class,
        GoalData::class,
        UserData::class,
        TypeData::class,
        SettingData::class
    ], version = 1, exportSchema = false
)
abstract class AchievementsDatabase : RoomDatabase() {

    abstract fun achievementDao(): AchievementDao
    abstract fun goalDao(): GoalDao
    abstract fun userDao(): UserDao
    abstract fun typeDao(): TypeDao
    abstract fun settingDao(): SettingDao
}