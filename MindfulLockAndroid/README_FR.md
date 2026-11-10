# MindfulLock — Projet Android (Kotlin & Jetpack Compose)

Application Android en français permettant de :
1. **Limiter l'accès aux applications (`Limites quotidiennes`)** : Choisir l'application, définir la durée quotidienne autorisée (ex. 15 min, 30 min, 1 h) et **sélectionner les jours de la semaine (`L M M J V S D`)** où la règle s'applique.
2. **Planifier des sessions de blocage (`Nouvelle planification`)** : Verrouillage d'appareil ou blocage d'applications par tranche horaire (`Heure de début` / `Heure de fin`), répétition par jour (`L M M J V S D`), options d'urgence, protection contre la fermeture, verrouillage après redémarrage et mode `Strict`.
3. **Écran de blocage (`Limite quotidienne atteinte`)** : Superposition plein écran avec exercice de respiration (`Inspire` / `Expire`) et horloge en direct.

## Structure du projet
- `app/src/main/AndroidManifest.xml` : Déclaration des permissions (`PACKAGE_USAGE_STATS`, `SYSTEM_ALERT_WINDOW`, `BIND_ACCESSIBILITY_SERVICE`, `RECEIVE_BOOT_COMPLETED`).
- `app/src/main/java/com/mindfullock/app/data/RuleEntities.kt` : Base de données Room (`AppLimitRuleEntity`, `SchedulePlanEntity`, `MindfulLockDao`).
- `app/src/main/java/com/mindfullock/app/service/AppBlockerService.kt` : Service d'accessibilité surveillant l'application active, le jour de la semaine (`L-D`) et le quota quotidien via `UsageStatsManager`.
- `app/src/main/java/com/mindfullock/app/ui/MainActivity.kt` : Interface utilisateur Jetpack Compose en français (thème sombre violet `#8264FF`).
- `app/src/main/java/com/mindfullock/app/ui/LockOverlayActivity.kt` : Écran `LIMITE QUOTIDIENNE ATTEINTE` avec animation de respiration.
