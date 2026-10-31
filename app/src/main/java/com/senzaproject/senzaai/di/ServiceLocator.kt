package com.senzaproject.senzaai.di

import android.app.Application
import androidx.compose.runtime.staticCompositionLocalOf
import com.senzaproject.senzaai.ai.AiClient
import com.senzaproject.senzaai.ai.AiSettingsStore
import com.senzaproject.senzaai.ai.SecureStore
import com.senzaproject.senzaai.ai.SmartDiagnostics
import com.senzaproject.senzaai.data.DeviceRepository
import com.senzaproject.senzaai.data.OperationLog
import com.senzaproject.senzaai.data.SessionContext
import com.senzaproject.senzaai.data.UiPrefs
import com.senzaproject.senzaai.monitor.HistoryStore
import com.senzaproject.senzaai.monitor.PerformanceMonitor
import com.senzaproject.senzaai.platform.AccessManager

/**
 * Dependency container manual. Dipilih agar build tetap sederhana dan stabil tanpa kapt/ksp;
 * antarmuka kelas sudah dipisah sehingga dapat dipindah ke Hilt tanpa mengubah layer domain.
 */
class ServiceLocator(val app: Application) {
    val operationLog by lazy { OperationLog(app) }
    val device by lazy { DeviceRepository(app) }
    val access by lazy { AccessManager(app, operationLog) }
    val editor by lazy { com.senzaproject.senzaai.platform.root.RootEditor(app, access, operationLog) }
    val secure by lazy { SecureStore(app) }
    val aiSettings by lazy { AiSettingsStore(app, secure) }
    val aiClient by lazy { AiClient() }
    val diagnostics by lazy { SmartDiagnostics(app, device) }
    val monitor by lazy { PerformanceMonitor(device, access) }
    val history by lazy { HistoryStore(app) }
    val uiPrefs by lazy { UiPrefs(app) }
    val session = SessionContext()
}

val LocalServices = staticCompositionLocalOf<ServiceLocator> { error("ServiceLocator belum disediakan") }
