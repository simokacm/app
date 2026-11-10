package com.mindfullock.app.service

import android.accessibilityservice.AccessibilityService
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.mindfullock.app.data.MindfulLockDatabase
import com.mindfullock.app.ui.LockOverlayActivity
import kotlinx.coroutines.*
import java.time.LocalDate
import java.util.Calendar

class AppBlockerService : AccessibilityService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val foregroundPkg = event.packageName?.toString() ?: return
        if (foregroundPkg == packageName) return

        scope.launch {
            val dao = MindfulLockDatabase.getInstance(applicationContext).dao()
            val todayIso = LocalDate.now().dayOfWeek.value // 1 = Lundi .. 7 = Dimanche
            val rules = dao.getEnabledAppLimits()

            val matchingRule = rules.firstOrNull {
                it.packageName == foregroundPkg && it.isActiveOnDay(todayIso)
            } ?: return@launch

            val usedMinutes = getTodayUsageMinutes(foregroundPkg)
            if (usedMinutes >= matchingRule.dailyLimitMinutes) {
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@AppBlockerService, LockOverlayActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        putExtra("APP_NAME", matchingRule.appLabel)
                        putExtra("LIMIT_MINUTES", matchingRule.dailyLimitMinutes)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun getTodayUsageMinutes(targetPackage: String): Int {
        val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val stats = usm.queryAndAggregateUsageStats(cal.timeInMillis, System.currentTimeMillis())
        val totalMs = stats[targetPackage]?.totalTimeInForeground ?: 0L
        return (totalMs / 60000L).toInt()
    }

    override fun onInterrupt() {}
}
