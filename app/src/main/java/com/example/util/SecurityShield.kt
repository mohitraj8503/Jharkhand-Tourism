package com.example.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Debug
import java.io.File

/**
 * SecurityShield: Anti-Reverse Engineering, Anti-Debugging, and Anti-Tampering Shield
 * Provides layered security against dynamic analysis, Frida hooking, debugger attachment,
 * and rooted/tampered environments.
 */
object SecurityShield {

    /**
     * Check if a debugger is currently attached or waiting.
     */
    fun isDebuggerAttached(): Boolean {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    }

    /**
     * Check if the application is running in debuggable mode when in production.
     */
    fun isAppDebuggable(context: Context): Boolean {
        return (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    /**
     * Detects root indicators, superuser binaries, and test-keys build signatures.
     */
    fun isDeviceRooted(): Boolean {
        val buildTags = Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }

        val suPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        for (path in suPaths) {
            try {
                if (File(path).exists()) return true
            } catch (_: Exception) {
                // Ignore file access restriction
            }
        }
        return false
    }

    /**
     * Detects Frida or common dynamic instrumentation tool artifacts in memory maps.
     */
    fun isDynamicHookingDetected(): Boolean {
        return try {
            val mapsFile = File("/proc/self/maps")
            if (mapsFile.exists()) {
                val mapsContent = mapsFile.readText()
                mapsContent.contains("frida") ||
                        mapsContent.contains("xposed") ||
                        mapsContent.contains("substrate") ||
                        mapsContent.contains("gadget")
            } else {
                false
            }
        } catch (_: Exception) {
            false
        }
    }
}
