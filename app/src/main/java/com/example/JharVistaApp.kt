package com.example

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import okhttp3.OkHttpClient

class JharVistaApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                // Wikimedia Commons and many CDNs block generic okhttp/* User-Agents with 403 Forbidden.
                // Providing a descriptive User-Agent complying with Wikimedia robot policy ensures
                // all authentic Jharkhand destination images load reliably.
                val authenticatedRequest = originalRequest.newBuilder()
                    .header(
                        "User-Agent",
                        "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                    )
                    .build()
                chain.proceed(authenticatedRequest)
            }
            .build()

        return ImageLoader.Builder(this)
            .okHttpClient(okHttpClient)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("jharvista_image_cache"))
                    .maxSizeBytes(60L * 1024 * 1024)
                    .build()
            }
            .respectCacheHeaders(false)
            .crossfade(true)
            .build()
    }
}
