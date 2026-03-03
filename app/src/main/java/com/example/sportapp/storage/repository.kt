package com.example.sportapp.storage


import kotlinx.coroutines.flow.Flow

class repository (private val dao: dao) {

    companion object {
        @Volatile
        private var instance: repository? = null

        fun returnInstance(dao: dao) = instance ?: synchronized(this) {
            instance ?: repository(dao).also { instance = it }
        }

    }

    fun returnInsertActivity(activity: Activity) = dao.createActivity(activity)

    fun returnDeleteActivity(activity: Activity) = dao.deleteActivity(activity)

    fun returnAllActivities(): Flow<List<Activity>> = dao.getAllActivities()

    fun returnAllPoints(): Flow<TotalPoints?> = dao.getAllPoints()

    fun ensurePointsExist() {
        dao.initiatePoints(TotalPoints(databaseId = 1, totalPoints = 0))
    }

    fun returnSubtractPoints(resetPointsValue: Long) = dao.subtractPoints(resetPointsValue = resetPointsValue)

    fun addPoints(amount: Long) {
        dao.addToTotalPoints(amount)
    }


    fun returnUnlockedAchievements(): Flow<List<Achievement>> = dao.getUnlockedAchievements()


    fun checkTotalMilestones() {

        // 1. Aktuelle Gesamtwerte aus der Datenbank holen
        val totalActivities = dao.getTotalActivitiesCount()
        val totalPointsReached = dao.getTotalPoints() ?: 0L
        val totalDurationMillis = dao.getTotalDuration() ?: 0L

        // Umrechnung für leichtere Vergleiche
        val totalPointsAcquired = totalPointsReached
        val totalDurationHours = totalDurationMillis / (1000L * 60 * 60)

        // 2. Prüfen: Aktivitäten-Meilensteine
        val lockedActivityAchievements = dao.getLockedAchievementsByType("ACTIVITIES")
        for (achievement in lockedActivityAchievements) {
            if (totalActivities >= achievement.requiredValue) {
                unlock(achievement)
            }
        }

        // 3. Prüfen: Distanz-Meilensteine
        val lockedPointsAchievements = dao.getLockedAchievementsByType("POINTS")
        for (achievement in lockedPointsAchievements) {
            if (totalPointsAcquired >= achievement.requiredValue) {
                unlock(achievement)
            }
        }

        // 4. Prüfen: Zeit-Meilensteine
        val lockedTimeAchievements = dao.getLockedAchievementsByType("TIME")
        for (achievement in lockedTimeAchievements) {
            if (totalDurationHours >= achievement.requiredValue) {
                unlock(achievement)
            }
        }
    }

    private fun unlock(achievement: Achievement) {
        // Status auf true setzen und in DB updaten
        val unlockedAchievement = achievement.copy(isUnlocked = true)
        dao.updateAchievement(unlockedAchievement)

        // Hier könntest du später ein Event feuern, um ein Konfetti-Popup im UI zu zeigen!
        println("ERFOLG FREIGESCHALTET: ${achievement.title}")
    }

    fun initAchievements() {
        val initialList = listOf(
            Achievement("act_10", "10 Aktivitäten abgeschlossen", "ACTIVITIES", 2),
            Achievement("act_25", "25 Aktivitäten abgeschlossen", "ACTIVITIES", 4),
            Achievement("act_50", "50 Aktivitäten abgeschlossen", "ACTIVITIES", 6),

            Achievement("pnts_100", "100 Punkte erreicht", "POINTS", 100),
            Achievement("pnts_500", "500 Punkte erreicht", "POINTS", 500),
            Achievement("pnts_2500", "2.500 Punkte erreicht", "POINTS", 2500),

            Achievement("time_24", "24 Stunden gefahren", "TIME", 24),
            Achievement("time_72", "72 Stunden gefahren", "TIME", 72),
            Achievement("time_240", "240 Stunden gefahren", "TIME", 240)
        )
        dao.insertInitialAchievements(initialList)
    }

}