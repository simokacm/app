package com.mindfullock.app.data

import android.content.Context
import androidx.room.*

@Entity(tableName = "app_daily_limits")
data class AppLimitRuleEntity(
    @PrimaryKey val packageName: String,
    val appLabel: String,
    val dailyLimitMinutes: Int,
    val activeDaysCsv: String, // "1,2,3,4,5,6,7" (1=Lundi .. 7=Dimanche)
    val remindFiveMinBefore: Boolean = true,
    val isEnabled: Boolean = true
) {
    fun isActiveOnDay(isoDayOfWeek: Int): Boolean {
        return activeDaysCsv.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
            .contains(isoDayOfWeek)
    }
}

@Entity(tableName = "schedules")
data class SchedulePlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val deviceFullLock: Boolean,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val activeDaysCsv: String,
    val blockedPackagesCsv: String,
    val emergencyUnlocksAllowed: Int = 0,
    val preventUninstall: Boolean = false,
    val lockAfterReboot: Boolean = true,
    val strictMode: Boolean = false
)

@Dao
interface MindfulLockDao {
    @Query("SELECT * FROM app_daily_limits WHERE isEnabled = 1")
    suspend fun getEnabledAppLimits(): List<AppLimitRuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAppLimit(rule: AppLimitRuleEntity)

    @Delete
    suspend fun deleteAppLimit(rule: AppLimitRuleEntity)

    @Query("SELECT * FROM schedules")
    suspend fun getAllSchedules(): List<SchedulePlanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: SchedulePlanEntity)
}

@Database(entities = [AppLimitRuleEntity::class, SchedulePlanEntity::class], version = 1)
abstract class MindfulLockDatabase : RoomDatabase() {
    abstract fun dao(): MindfulLockDao

    companion object {
        @Volatile private var INSTANCE: MindfulLockDatabase? = null
        fun getInstance(context: Context): MindfulLockDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MindfulLockDatabase::class.java,
                    "mindfullock.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
