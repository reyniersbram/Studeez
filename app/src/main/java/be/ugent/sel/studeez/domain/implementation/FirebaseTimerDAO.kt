package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.timer_info.*
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.TimerDAO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseTimerDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val configurationService: FirebaseConfigurationService,
    private val auth: AccountDAO
) : TimerDAO {

    override fun getUserTimers(): Flow<List<TimerInfo>> {
        return currentUserTimersCollection()
            .snapshots()
            .map { it.toObjects(TimerJson::class.java) }
            .map { ToTimerConverter().convertToTimerInfoList(it) }
    }

    override fun getAllTimers(): Flow<List<TimerInfo>> {
        // Wrap default timers in een flow en combineer met de userTimer flow.
        val defaultTimers: List<TimerInfo> = configurationService.getDefaultTimers()
        val defaultTimersFlow: Flow<List<TimerInfo>> = flowOf(defaultTimers)
        val userTimersFlow: Flow<List<TimerInfo>> =  getUserTimers()
        return defaultTimersFlow.combine(userTimersFlow) { defaultTimersList, userTimersList ->
            defaultTimersList + userTimersList
        }
    }

    override fun saveTimer(newTimer: TimerInfo) {
        currentUserTimersCollection().add(newTimer.asJson())
    }

    override fun updateTimer(timerInfo: TimerInfo) {
        currentUserTimersCollection().document(timerInfo.id).set(timerInfo.asJson())
    }

    override fun deleteTimer(timerInfo: TimerInfo) {
        currentUserTimersCollection().document(timerInfo.id).delete()
    }

    private fun currentUserTimersCollection(): CollectionReference =
        firestore.collection(FireBaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FireBaseCollections.TIMER_COLLECTION)

}