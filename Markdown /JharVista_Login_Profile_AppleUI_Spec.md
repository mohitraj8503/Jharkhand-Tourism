# JharVista — Login, Profile & Apple-Style UI Specification

**Document type:** Complete spec for Welcome/Login/Signup screens, Profile section (top-right PS avatar), and Apple-style UI guidelines for the JharVista app
**App name:** JharVista (NOT TRU — the app is called JharVista)
**Target platform:** Native Android (Kotlin + Jetpack Compose)
**Purpose:** Define the login flow, profile screen, and Apple-grade UI design so Google AI Studio can generate the complete working screens

---

## 0. CRITICAL INSTRUCTIONS FOR GOOGLE AI STUDIO

> **READ THIS FIRST.**

1. **The app name is JHARVISTA.** Not TRU. Not "Jharkhand Tourism." The app is called **JharVista**. Use this name everywhere — on the splash screen, login screen, app bar, profile screen, and share text. The AI assistant can be called "JharVista AI."

2. **The FIRST screen the user sees is the Welcome/Login screen.** Not the Home screen. When the app launches, it shows a beautiful welcome screen → login/signup → then Home. No exceptions.

3. **The top-right "PS" avatar must open a full Profile screen** with a real user photo (placeholder initially), user name, email, trip stats, settings, and logout. This profile screen must follow Apple's profile/settings UI design language (like iOS Settings app or Apple Account profile).

4. **The entire UI must look like an Apple app.** Think Apple Maps, Apple Travel, iOS Settings, Apple Music — clean, premium, large rounded corners, SF Pro typography, soft shadows, smooth transitions, generous whitespace. Material 3 is the base, but the aesthetic is Apple.

5. **User login must work.** Use Firebase Auth (Email/Password + Google Sign-In). If Firebase is not configured, use a local Room-based auth stub that validates email format and stores user data locally. The login must feel real — email field, password field, show/hide password, forgot password link, signup link.

6. **The "PS" avatar on the Home screen top-right must show the logged-in user's initials or photo.** If the user's name is "Paul Steven," it shows "PS." If the user uploaded a photo, it shows the photo. Tapping it opens the Profile screen.

---

## 1. APP NAME & BRANDING

- **App name:** JharVista
- **Tagline:** "Tourism in Jharkhand" (as shown in the Visily prototype)
- **Logo:** A stylized "JV" monogram or the word "JharVista" in a clean, premium sans-serif font. The "Jhar" part can be in forest green, "Vista" in lime accent — or the whole word in white on dark green.
- **AI assistant name:** JharVista AI

---

## 2. WELCOME / SPLASH SCREEN (First screen on app launch)

> **This is the very first screen the user sees when they open JharVista.**

### Visual design
- **Background:** Full-screen dark forest green (`#0B3D2E`) or a gradient from dark green to charcoal.
- **Center:**
  - Large JharVista logo (white or lime, 32sp bold)
  - Tagline below: "Tourism in Jharkhand" (white 60% opacity, 14sp)
  - Below the tagline, a hero image — a beautiful photo of a Jharkhand destination (Dassam Falls or Patratu Valley) as a subtle, low-opacity background or a centered rounded image card.
- **Animation:** Logo fades in + scales up (300ms). After 2 seconds, smooth transition to the Login screen.
- **Bottom:** "Powered by JharVista AI" text (white 40% opacity, 12sp)

### Hero image (use real Jharkhand photo)
```
imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg"
```
or
```
imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg"
```

### Kotlin Compose structure
```kotlin
@Composable
fun WelcomeScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 second splash
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B3D2E), // dark forest green
                        Color(0xFF1A2332)  // dark navy
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo
            Text(
                text = "JharVista",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // Tagline
            Text(
                text = "Tourism in Jharkhand",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
        // Bottom text
        Text(
            text = "Powered by JharVista AI",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
        )
    }
}
```

---

## 3. LOGIN SCREEN (Shown after splash)

> **Apple-style login screen. Clean, minimal, premium. Think Apple ID login.**

### Visual design
- **Background:** Clean white (`#F8F9FA`) or very light grey.
- **Top:** JharVista logo (forest green, 28sp bold) + "Tourism in Jharkhand" subtitle (grey, 14sp).
- **Center — Form card:**
  - Rounded card (white bg, 24dp radius, soft shadow, 24dp padding)
  - **Email field:** outlined text field with email icon, placeholder "Email", 16sp input, keyboard = email
  - **Password field:** outlined text field with lock icon, placeholder "Password", show/hide toggle (eye icon), 16sp input, keyboard = password
  - **Forgot password?** link (right-aligned, lime/green text, 13sp)
  - **Login button:** full-width, forest green bg, white text, 52dp height, 16dp radius, "Log In" text, 16sp semibold
  - **Divider:** "or" with horizontal lines on both sides (grey)
  - **Google Sign-In button:** full-width, white bg with border, Google "G" logo + "Continue with Google" text, 52dp height
  - **Signup link:** "Don't have an account? Sign Up" (centered, 14sp, "Sign Up" in green/bold)
- **Bottom:** "By continuing, you agree to JharVista's Terms of Service and Privacy Policy" (grey, 11sp, centered)

### Layout (top to bottom)
1. Spacer (top, 48dp)
2. JharVista logo + tagline
3. Spacer (32dp)
4. Form card (email, password, forgot password, login button, divider, Google button, signup link)
5. Spacer (weight 1)
6. Terms text
7. Spacer (bottom, 24dp)

### Kotlin Compose structure
```kotlin
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo
        Text("JharVista", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
        Text("Tourism in Jharkhand", fontSize = 14.sp, color = Color(0xFF6B7280))

        Spacer(modifier = Modifier.height(32.dp))

        // Form card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Forgot password
                Text(
                    "Forgot password?",
                    fontSize = 13.sp,
                    color = Color(0xFF0B3D2E),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Login button
                Button(
                    onClick = { onLoginSuccess() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text("  or  ", fontSize = 13.sp, color = Color(0xFF6B7280))
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Google Sign-In
                OutlinedButton(
                    onClick = { /* Google Sign-In */ },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Text("G  Continue with Google", fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Signup link
                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withLink(SpanStyle(color = Color(0xFF0B3D2E), fontWeight = FontWeight.Bold)) {
                            append("Sign Up")
                        }
                    },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Terms
        Text(
            "By continuing, you agree to JharVista's Terms of Service and Privacy Policy",
            fontSize = 11.sp,
            color = Color(0xFF9CA3AF),
            textAlign = TextAlign.Center
        )
    }
}
```

---

## 4. SIGNUP SCREEN

> **Same Apple aesthetic as Login. Clean, minimal.**

### Fields (top to bottom)
1. **Full Name** — text field, placeholder "Full Name"
2. **Email** — text field, email keyboard
3. **Password** — text field with show/hide toggle
4. **Confirm Password** — text field with show/hide toggle
5. **Sign Up button** — full-width, forest green, 52dp height, 16dp radius
6. **Divider** — "or"
7. **Google Sign-Up button** — same as login
8. **Login link** — "Already have an account? Log In"

### Validation
- Email must be valid format (contains @ and .)
- Password must be 6+ characters
- Confirm password must match
- Show inline error messages below each field (red, 12sp)
- On success → navigate to Home screen

---

## 5. PROFILE SCREEN (opened from top-right PS avatar)

> **This is the screen that opens when the user taps the "PS" circular avatar on the Home screen top-right. It must look like Apple's profile/settings — clean, premium, with a large profile header and grouped settings sections.**

### Visual design — Apple-style profile

#### Header section (top)
- **Background:** forest green gradient (`#0B3D2E` → `#1A4A3A`)
- **Profile photo:** Large circular avatar (96dp), centered or left-aligned. Shows the user's photo if uploaded, or their initials on a lime circle (e.g., "PS" for Paul Steven).
- **Name:** "Paul Steven" (white, 22sp bold)
- **Email:** "paul.steven@email.com" (white 70% opacity, 14sp)
- **Edit button:** small "Edit Profile" text button (white outline, top-right)

#### Stats row (below header)
- A horizontal row of 3 stat cards (white bg, rounded, centered):
  - **Trips:** "12" + "Trips Planned" label
  - **Places:** "8" + "Saved Places" label
  - **Countries:** "1" + "State Explored" label (or "Places Visited")

#### Settings sections (grouped, iOS Settings style)
Each section is a white card with rounded corners (16dp), and each item has an icon, label, and chevron arrow.

**Section 1: Account**
- **Personal Info** (icon: person) → edit name, email, phone
- **Change Password** (icon: lock) → password change form
- **Linked Accounts** (icon: link) → Google account status

**Section 2: Preferences**
- **Language** (icon: translate) → English / हिंदी / संथाली selector
- **Notifications** (icon: notifications) → toggle switches for trip reminders, AI suggestions, event alerts
- **Eco-Mode** (icon: eco) → toggle to prioritize eco-friendly recommendations

**Section 3: Trips & Data**
- **My Trips** (icon: suitcase) → navigate to My Trips screen
- **Saved Places** (icon: bookmark) → list of saved destinations
- **Payment Methods** (icon: credit_card) → saved cards / wallet
- **Downloaded Guides** (icon: download) → offline audio guides

**Section 4: About**
- **About JharVista** (icon: info) → app version, description
- **Privacy Policy** (icon: privacy_tip) → legal text
- **Terms of Service** (icon: description) → legal text
- **Rate JharVista** (icon: star) → Play Store link

**Section 5: Logout**
- **Log Out** button (red text, centered, full-width, 52dp height) — clears session and returns to Login screen

### Apple-style settings item component
```kotlin
@Composable
fun SettingsRow(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    onClick: () -> Unit,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, color = Color(0xFF1A1A1A))
        Spacer(modifier = Modifier.weight(1f))
        trailing?.invoke() ?: Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF)
        )
    }
}
```

### Edit Profile sub-screen
- Opens when "Edit Profile" is tapped
- Shows: profile photo (tappable to upload new), name field, email field (read-only), phone field
- "Save Changes" button (forest green, full-width)
- Camera/gallery picker for photo upload (use Android `ActivityResultContracts.GetContent`)

---

## 6. HOME SCREEN — TOP-RIGHT AVATAR INTEGRATION

> **The Home screen's top-right must show the logged-in user's avatar (not just "PS" hardcoded).**

### How it works
1. After login, store the user object (name, email, photoUrl) in a `UserViewModel` or session manager.
2. On the Home screen top bar:
   - Left: hamburger icon (opens sidebar)
   - Center: "Current Location" + "Ranchi, Jharkhand"
   - Right: **circular avatar** (40dp)
     - If user has a photo URL → show the photo (loaded via Coil `AsyncImage`)
     - If no photo → show initials on a lime circle (extract first letters of first + last name)
3. Tapping the avatar opens the Profile screen (Section 5).

### Avatar composable
```kotlin
@Composable
fun ProfileAvatar(user: User?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFFC6F432)) // lime accent
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (user?.photoUrl != null) {
            AsyncImage(
                model = user.photoUrl,
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = user?.initials ?: "PS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B3D2E)
            )
        }
    }
}
```

### Initials extraction
```kotlin
val User.initials: String
    get() {
        val parts = name.split(" ")
        return if (parts.size >= 2) {
            "${parts[0].first()}${parts[1].first()}".uppercase()
        } else {
            name.take(2).uppercase()
        }
    }
```

---

## 7. AUTHENTICATION FLOW

### Navigation flow
```
App Launch
  → WelcomeScreen (2s splash)
    → LoginScreen (if not logged in)
      → Login success → HomeScreen
    → SignupScreen (if user taps "Sign Up")
      → Signup success → HomeScreen
    → Already logged in (session exists) → HomeScreen directly (skip login)
```

### Auth implementation options

#### Option A: Firebase Auth (preferred)
```kotlin
// build.gradle
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.android.gms:play-services-auth")

// Login
firebaseAuth.signInWithEmailAndPassword(email, password)
    .addOnSuccessListener { onLoginSuccess() }
    .addOnFailureListener { /* show error */ }

// Google Sign-In
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("YOUR_WEB_CLIENT_ID")
    .requestEmail()
    .build()
```

#### Option B: Local Room-based auth (if no Firebase)
```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val passwordHash: String, // store hash, not plaintext
    val photoUrl: String? = null
)

@Dao
interface UserDao {
    @Insert suspend fun insert(user: UserEntity)
    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    suspend fun login(email: String, passwordHash: String): UserEntity?
}

// Session manager
class SessionManager(private val context: Context) {
    fun saveUser(email: String) {
        context.getSharedPreferences("jharvista", MODE_PRIVATE)
            .edit().putString("user_email", email).apply()
    }
    fun isLoggedIn(): Boolean {
        return context.getSharedPreferences("jharvista", MODE_PRIVATE)
            .contains("user_email")
    }
    fun logout() {
        context.getSharedPreferences("jharvista", MODE_PRIVATE)
            .edit().clear().apply()
    }
}
```

---

## 8. APPLE-STYLE UI DESIGN SYSTEM (Updated)

> **The entire app — every screen — must follow Apple's design language. Here are the specific rules.**

### 8.1 Design principles (Apple HIG)
1. **Clarity** — legible text, precise icons, smooth animations. Content is king.
2. **Deference** — the UI is fluid, unobtrusive. No heavy borders, no visual noise. Let content shine.
3. **Depth** — realistic motion, layered views, subtle shadows create hierarchy.
4. **Direct manipulation** — tap, swipe, pinch gestures feel natural and responsive.
5. **Consistency** — same spacing, fonts, colors, and component styles across all screens.

### 8.2 Color palette (Apple-inspired)
| Token | Value | Use |
|-------|-------|-----|
| `PrimaryGreen` | `#0B3D2E` (forest green) | Primary actions, dark surfaces, brand |
| `LimeAccent` | `#C6F432` (lime) | Highlights, active states, badges |
| `Background` | `#F2F2F7` (Apple system grey 6) | App background — this is the exact colour Apple uses |
| `SurfaceCard` | `#FFFFFF` | Card background |
| `GroupedBg` | `#F2F2F7` | Settings section background (Apple style) |
| `TextPrimary` | `#000000` | Primary text (Apple uses pure black) |
| `TextSecondary` | `#3C3C43` with 60% opacity | Secondary text (Apple's secondary label) |
| `TextTertiary` | `#3C3C43` with 30% opacity | Tertiary/placeholder text |
| `Separator` | `#3C3C43` with 29% opacity | Hairline separators (Apple style) |
| `SeparatorOpaque` | `#C6C6C8` | Opaque separator |
| `NavySidebar` | `#1A2332` | Sidebar drawer background |
| `EcoBadge` | `#34C759` (Apple green) | Eco badges |
| `Error` | `#FF3B30` (Apple red) | Errors, destructive actions |
| `Warning` | `#FF9500` (Apple orange) | Warnings, footfall medium |
| `LinkBlue` | `#007AFF` (Apple blue) | Links (if needed) |

### 8.3 Typography (Apple SF Pro style)
| Style | Size | Weight | Use |
|-------|------|--------|-----|
| LargeTitle | 34sp | Bold | Screen titles (like Apple's large title) |
| Title1 | 28sp | Bold | Welcome screen, profile name |
| Title2 | 22sp | Semibold | Section headers |
| Title3 | 20sp | Semibold | Card titles |
| Headline | 17sp | Semibold | List item labels |
| Body | 17sp | Regular | Body text (Apple body is 17pt) |
| Callout | 16sp | Regular | Secondary descriptions |
| Subhead | 15sp | Regular | Subtitles |
| Footnote | 13sp | Regular | Small text, timestamps |
| Caption1 | 12sp | Regular | Badges, metadata |
| Caption2 | 11sp | Regular | Fine print |

**Font:** Use `Inter` (closest to SF Pro on Android) or `Roboto Flex`. If possible, bundle SF-Pro-like font.

### 8.4 Spacing & layout
- **Screen margin:** 20dp left/right (Apple's standard margin)
- **Card padding:** 16-20dp
- **Card corner radius:** 16dp (Apple uses 10pt for most cards, but 16dp looks good on Android)
- **Button height:** 52dp (taller for touch accessibility)
- **Button radius:** 14dp
- **List item height:** 56dp minimum
- **Section spacing:** 24dp between sections
- **Hairline separator:** 0.5dp height

### 8.5 Component specs (Apple-style)

#### Cards
```kotlin
Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Apple: no shadow, use separator
    modifier = Modifier.fillMaxWidth()
)
```
Note: Apple uses very subtle shadows or no shadows at all — they rely on background contrast and hairline separators.

#### Settings list (iOS style)
```kotlin
LazyColumn(
    modifier = Modifier.background(Color(0xFFF2F2F7)) // Apple grouped background
) {
    item {
        // Section header (uppercase, grey, 13sp)
        Text("ACCOUNT", fontSize = 13.sp, color = Color(0xFF6B7280),
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 8.dp))
    }
    item {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth()
        ) {
            Column {
                SettingsRow(icon = person, "Personal Info", ...)
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp,
                    modifier = Modifier.padding(start = 56.dp))
                SettingsRow(icon = lock, "Change Password", ...)
            }
        }
    }
}
```

#### Toggle switch (iOS style)
```kotlin
Switch(
    checked = state,
    onCheckedChange = { state = it },
    colors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        checkedTrackColor = Color(0xFF34C759), // Apple green
        uncheckedThumbColor = Color.White,
        uncheckedTrackColor = Color(0xFFE5E5EA)
    )
)
```

#### Bottom navigation
- 64dp height
- White background with hairline separator on top (0.5dp, `#E5E5EA`)
- Active icon + label: forest green or lime
- Inactive: grey `#9CA3AF`
- Apple-like: no pill background on active, just colour change

#### Text fields (Apple style)
```kotlin
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFF2F2F7), RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFF0B3D2E),
        unfocusedBorderColor = Color(0xFFE5E5EA),
        backgroundColor = Color(0xFFF2F2F7)
    )
)
```

### 8.6 Animations (Apple-style)
- **Page transitions:** slide horizontally (like iOS push), 350ms, `EaseInOut`
- **Modal sheets:** slide up from bottom, 400ms, with grabber handle at top
- **List item tap:** subtle background highlight (grey), no ripple
- **Card tap:** scale to 0.98, 100ms, spring back
- **Splash to login:** fade out + scale, 500ms
- **Profile header:** parallax scroll effect (header image moves slower than content)

---

## 9. SCREEN FLOW / NAVIGATION GRAPH

```
NavHost(startDestination = "welcome") {

    composable("welcome") {
        WelcomeScreen(onNavigateToLogin = { navController.navigate("login") })
    }

    composable("login") {
        LoginScreen(
            onLoginSuccess = {
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true }
                }
            },
            onNavigateToSignup = { navController.navigate("signup") }
        )
    }

    composable("signup") {
        SignupScreen(
            onSignupSuccess = {
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true }
                }
            },
            onNavigateToLogin = { navController.popBackStack() }
        )
    }

    composable("home") {
        JharVistaMainScreen(
            onProfileClick = { navController.navigate("profile") }
        )
    }

    composable("profile") {
        ProfileScreen(
            onLogout = {
                sessionManager.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }

    // Sidebar routes
    composable("jharkhand_glance") { JharkhandGlanceScreen() }
    composable("events") { EventsScreen() }
    composable("hospitality") { HospitalityScreen() }
    composable("souvenirs") { SouvenirsScreen() }
    composable("heli_tourism") { HeliTourismScreen() }
    composable("audio_guide") { AudioGuideScreen() }
    composable("share") { ShareScreen() }
}
```

### Session check on app start
```kotlin
val startDestination = if (sessionManager.isLoggedIn()) "home" else "welcome"
NavHost(navController, startDestination = startDestination) { ... }
```

---

## 10. USER DATA MODEL

```kotlin
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
    val phone: String? = null,
    val language: String = "English",
    val ecoMode: Boolean = true,
    val notificationsEnabled: Boolean = true
)

val User.initials: String
    get() {
        val parts = name.trim().split(" ")
        return if (parts.size >= 2) {
            "${parts[0].first()}${parts[1].first()}".uppercase()
        } else if (name.isNotEmpty()) {
            name.take(2).uppercase()
        } else {
            "JV" // JharVista default
        }
    }
```

---

## 11. UPDATED HOME SCREEN (JharVista branding)

> **The Home screen from the prototype, but with JharVista branding and the working avatar.**

### Top bar
- Left: hamburger icon (opens sidebar)
- Center-left: pin icon + "CURRENT LOCATION" (grey caps, 11sp) + "Ranchi, Jharkhand" (bold, 17sp)
- Right: **ProfileAvatar** (40dp circular, lime bg with initials or user photo)

### Filter chips
- "Trending" (active, lime bg) | "Top Picks" | "Nearby"

### AI Assistant card
- Lime gradient card
- "JHARVISTA AI" tag (small, yellow/gold)
- "Plan Your Perfect Trip In Seconds With JharVista AI"
- "Let us build a personalized itinerary based on your budget, pace, and eco-friendly style."
- "Start Planning →" link

### Curated For You
- "Curated For You" (bold, 20sp) + "3 destinations" (grey, 13sp)
- Horizontal carousel of destination cards with REAL Jharkhand photos:
  - Betla National Park (₹1,200/person) with "Eco-Choice" badge + "Explore Itinerary" button
  - Hundru Falls (Ranchi, Jharkhand)
  - Dassam Falls (Ranchi, Jharkhand)

### Smart Footfall Prediction card
- Lightbulb icon
- "Smart Footfall Prediction"
- "High footfall (≈22%) predicted this weekend. Consider Panch Gagh Falls for zero wait times."

### Bottom nav
- Home (active, green) | Plan | Trips | Wallet

---

## 12. ACCEPTANCE CRITERIA

1. App launches → shows **JharVista Welcome screen** (splash with logo + Dassam Falls photo) for 2 seconds → transitions to **Login screen**.
2. Login screen has: email field, password field (with show/hide), forgot password link, Log In button, Google Sign-In button, Sign Up link.
3. After login → Home screen shows **"JharVista"** branding and the user's avatar (initials or photo) in the top-right.
4. Tapping the avatar → opens **Profile screen** (Apple-style, with header, stats, grouped settings sections, logout button).
5. Profile screen looks like iOS Settings — `#F2F2F7` background, white grouped cards, hairline separators, chevron arrows.
6. Logout button → clears session → returns to Login screen.
7. All screens use Apple-style typography (17sp body, bold titles, proper hierarchy), spacing (20dp margins), and colours (Apple system colours).
8. The sidebar still has exactly 7 items (unchanged from previous spec).
9. All image URLs from the previous document (`TRU_Image_Assets_and_Sidebar_Spec.md`) are used.
10. The app name is **JharVista** everywhere — splash, login, profile, share text, AI assistant.

---

*End of document. Hand this to Google AI Studio alongside the previous two documents. Instruct: "The app is called JharVista. Build the Welcome → Login → Home → Profile flow exactly as specified. The profile screen must look like Apple iOS Settings. The top-right avatar must show the logged-in user's initials/photo and open the profile screen. Use Apple design language for ALL screens."*
