# =========================================================
# JharVista Anti-Reverse Engineering & Obfuscation Rules
# =========================================================

# 1. Aggressive Obfuscation & Bytecode Optimization
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-overloadaggressively
-useuniqueclassmembernames

# 2. Package Flattening & Repackaging (Destroy original package structure)
-repackageclasses ''
-flattenpackagehierarchy ''

# 3. Source & Line Number Stripping
-renamesourcefileattribute ""
-keepattributes !SourceFile,!LineNumberTable,!LocalVariableTable,!LocalVariableTypeTable

# 4. Strip Logging Statements to prevent information leakage
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

# 5. Room SQLite Database Preservation
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keep class * extends androidx.room.migration.Migration
-dontwarn androidx.room.paging.**

# 6. Android Core Components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# 7. Jetpack Compose Rules
-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
    void <init>(androidx.compose.runtime.Composer, int);
}

# 8. Google Play Services Location & Google Maps
-keep class com.google.android.gms.location.** { *; }
-dontwarn com.google.android.gms.**

# 9. Kotlin Reflection & Coroutines
-keepattributes EnclosingMethod,InnerClasses,Signature
-dontwarn kotlinx.coroutines.**
-dontwarn kotlin.reflect.**

# 10. Security Shield Protection
-keep class com.example.util.SecurityShield { *; }
