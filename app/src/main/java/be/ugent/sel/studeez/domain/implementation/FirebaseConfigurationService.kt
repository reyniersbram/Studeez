package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.timer_info.*
import be.ugent.sel.studeez.domain.ConfigurationService
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseConfigurationService @Inject constructor() : ConfigurationService {

    init {
        // fetch configs elke keer als app wordt opgestart
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    private val remoteConfig
        get() = Firebase.remoteConfig

    override suspend fun fetchConfiguration(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    override fun getDefaultTimers(): List<TimerInfo> {
        val jsonString: String = remoteConfig[DEFAULT_TIMERS].asString()
        // Json is een lijst van timers
        val timerJsonList: List<TimerJson> = ToTimerConverter().jsonToTimerJsonList(jsonString)
        return ToTimerConverter().convertToTimerInfoList(timerJsonList)
    }

    companion object {
        private const val DEFAULT_TIMERS = "default_timers"
    }
}