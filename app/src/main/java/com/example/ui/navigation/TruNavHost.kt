package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.local.SessionManager
import com.example.data.repository.TruRepository
import com.example.ui.auth.LoginScreen
import com.example.ui.auth.SignupScreen
import com.example.ui.auth.WelcomeScreen
import com.example.ui.booking.BookingConfigScreen
import com.example.ui.booking.FlightSearchScreen
import com.example.ui.discovery.MapDiscoveryScreen
import com.example.ui.home.HomeScreen
import com.example.ui.plan.AIPlanningScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardSubtle
import com.example.ui.theme.SurfaceWarm
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.trips.MyTripsScreen
import com.example.ui.trips.TripTimelineScreen
import com.example.ui.wallet.WalletScreen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.ui.components.SidebarDrawer
import kotlinx.coroutines.launch

import com.example.ui.sidebar.AudioGuideScreen
import com.example.ui.sidebar.EventsScreen
import com.example.ui.sidebar.HeliTourismScreen
import com.example.ui.sidebar.HospitalityScreen
import com.example.ui.sidebar.JharkhandGlanceScreen
import com.example.ui.sidebar.ShareScreen
import com.example.ui.sidebar.SouvenirsScreen

@Composable
fun TruApp(repository: TruRepository) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val database = remember { com.example.data.local.TruDatabase.getInstance(context) }
    val emergencyContactRepository = remember {
        com.example.data.repository.EmergencyContactRepositoryImpl(
            dao = database.emergencyContactDao(),
            context = context
        )
    }
    val healthSafetyViewModel: com.example.ui.health.HealthSafetyViewModel = androidx.lifecycle.viewmodel.compose.viewModel {
        com.example.ui.health.HealthSafetyViewModel(emergencyContactRepository)
    }

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Plan,
        Screen.Trips,
        Screen.Wallet,
        Screen.HealthSafety
    )

    val showBottomBar = bottomNavItems.any { it.route == currentRoute }

    // Dynamic flight search state
    var searchOrigin by remember { mutableStateOf("Delhi (DEL)") }
    var searchDest by remember { mutableStateOf("Ranchi (IXR)") }
    var searchDate by remember { mutableStateOf("12 Aug") }
    var searchPassengers by remember { mutableStateOf(2) }
    var searchClass by remember { mutableStateOf("Economy") }
    var searchTripType by remember { mutableStateOf("One Way") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SidebarDrawer(
                onNavigate = { route ->
                    coroutineScope.launch { drawerState.close() }
                    navController.navigate(route)
                },
                onClose = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = SurfaceWarm,
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    androidx.compose.material3.Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("bottom_navigation_bar"),
                        color = Color.White,
                        shadowElevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.5.dp)
                                    .background(Color(0xFFD1D1D6))
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(49.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                bottomNavItems.forEach { screen ->
                                    val isSelected = currentRoute == screen.route
                                    val tintColor = if (isSelected) ForestGreen else Color(0xFF8E8E93)
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .clickable(
                                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                                indication = null
                                            ) {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title,
                                            tint = tintColor,
                                            modifier = Modifier.size(23.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = screen.title,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                            fontSize = 10.sp,
                                            color = tintColor,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            val startDestination = "welcome"
            
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp)
            ) {
                // Auth Flow
                composable("welcome") {
                    WelcomeScreen(
                        onNavigateNext = {
                            val destination = if (sessionManager.isLoggedIn()) Screen.Home.route else "login"
                            navController.navigate(destination) {
                                popUpTo("welcome") { inclusive = true }
                            }
                        }
                    )
                }

                composable("login") {
                    LoginScreen(
                        onLoginSuccess = { email ->
                            sessionManager.saveUser(email, "Paul Steven", null)
                            navController.navigate(Screen.Home.route) {
                                popUpTo("welcome") { inclusive = true }
                            }
                        },
                        onNavigateToSignup = { navController.navigate("signup") },
                        onContinueAsGuest = {
                            sessionManager.saveUser("guest@jharvista.local", "Guest User", null)
                            navController.navigate(Screen.Home.route) {
                                popUpTo("welcome") { inclusive = true }
                            }
                        }
                    )
                }

                composable("signup") {
                    SignupScreen(
                        onSignupSuccess = { email, name ->
                            sessionManager.saveUser(email, name, null)
                            navController.navigate(Screen.Home.route) {
                                popUpTo("welcome") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { navController.popBackStack() },
                        onContinueAsGuest = {
                            sessionManager.saveUser("guest@jharvista.local", "Guest User", null)
                            navController.navigate(Screen.Home.route) {
                                popUpTo("welcome") { inclusive = true }
                            }
                        }
                    )
                }
                
                composable("profile") {
                    ProfileScreen(
                        sessionManager = sessionManager,
                        onLogout = {
                            sessionManager.logout()
                            navController.navigate("login") {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        },
                        onBack = { navController.popBackStack() },
                        onNavigateToTrips = {
                            navController.navigate(Screen.Trips.route)
                        },
                        onNavigateToDestination = { destId ->
                            navController.navigate(Screen.DestinationDetail.createRoute(destId))
                        },
                        onNavigateToWallet = {
                            navController.navigate(Screen.Wallet.route)
                        }
                    )
                }

                // Tab 1: Home
                composable(Screen.Home.route) {
                    val initials = sessionManager.getUserName().split(" ").let { parts ->
                        if (parts.size >= 2) "${parts[0].first()}${parts[1].first()}".uppercase()
                        else if (sessionManager.getUserName().isNotEmpty()) sessionManager.getUserName().take(2).uppercase()
                        else "JV"
                    }
                    HomeScreen(
                        repository = repository,
                        onNavigateToPlan = {
                            navController.navigate(Screen.Plan.route)
                        },
                        onNavigateToBooking = { destination ->
                            navController.navigate(Screen.BookingConfig.createRoute(destination))
                        },
                        onNavigateToTimeline = { tripId ->
                            navController.navigate(Screen.TripTimeline.createRoute(tripId))
                        },
                        onNavigateToFlights = {
                            navController.navigate(Screen.FlightSearch.route)
                        },
                        onNavigateToMap = {
                            navController.navigate(Screen.MapDiscovery.route)
                        },
                        onProfileClick = {
                            navController.navigate("profile")
                        },
                        onMenuClick = {
                            coroutineScope.launch { drawerState.open() }
                        },
                        onNavigateToWhereToGo = {
                            navController.navigate(Screen.WhereToGo.route)
                        },
                        onNavigateToDestinationDetail = { destId ->
                            navController.navigate(Screen.DestinationDetail.createRoute(destId))
                        },
                        userInitials = initials,
                        userPhotoUrl = sessionManager.getUserPhotoUrl(),
                        sessionManager = sessionManager
                    )
                }

            // Tab 2: AI Plan
            composable(Screen.Plan.route) {
                AIPlanningScreen(
                    repository = repository,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToTimeline = { tripId ->
                        navController.navigate(Screen.TripTimeline.createRoute(tripId))
                    },
                    onNavigateToRentals = {
                        navController.navigate(Screen.EVRental.route)
                    },
                    onNavigateToHealthSafety = {
                        navController.navigate(Screen.HealthSafety.route)
                    },
                    onNavigateToEmergencyContacts = {
                        navController.navigate(Screen.EmergencyContacts.route)
                    }
                )
            }

            // Tab 3: My Trips
            composable(Screen.Trips.route) {
                MyTripsScreen(
                    repository = repository,
                    onNavigateToTimeline = { tripId ->
                        navController.navigate(Screen.TripTimeline.createRoute(tripId))
                    },
                    onAddNewTrip = {
                        navController.navigate(Screen.Plan.route)
                    }
                )
            }

            // Tab 4: Wallet
            composable(Screen.Wallet.route) {
                WalletScreen(repository = repository)
            }

            // Tab 5: Health & Safety
            composable(Screen.HealthSafety.route) {
                com.example.ui.health.HealthSafetyScreen(
                    viewModel = healthSafetyViewModel,
                    onNavigateToContacts = {
                        navController.navigate(Screen.EmergencyContacts.route)
                    },
                    onNavigateToAddContact = {
                        navController.navigate(Screen.AddEmergencyContact.route)
                    }
                )
            }

            // Health & Safety Sub-screens
            composable(Screen.EmergencyContacts.route) {
                com.example.ui.health.EmergencyContactsScreen(
                    viewModel = healthSafetyViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddContact = {
                        navController.navigate(Screen.AddEmergencyContact.route)
                    },
                    onNavigateToEditContact = { contactId ->
                        navController.navigate(Screen.EditEmergencyContact.createRoute(contactId))
                    }
                )
            }

            composable(Screen.AddEmergencyContact.route) {
                com.example.ui.health.AddEmergencyContactScreen(
                    contactId = null,
                    viewModel = healthSafetyViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditEmergencyContact.route,
                arguments = listOf(navArgument("contactId") { type = NavType.LongType })
            ) { backStackEntry ->
                val contactId = backStackEntry.arguments?.getLong("contactId") ?: 0L
                com.example.ui.health.AddEmergencyContactScreen(
                    contactId = contactId,
                    viewModel = healthSafetyViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Sub-screen: Booking Config
            composable(
                route = Screen.BookingConfig.route,
                arguments = listOf(navArgument("destination") { type = NavType.StringType })
            ) { backStackEntry ->
                val destination = backStackEntry.arguments?.getString("destination") ?: "Bali, Indonesia"
                BookingConfigScreen(
                    initialDestination = destination,
                    onNavigateBack = { navController.popBackStack() },
                    onSearchFlights = { from, to, date, passengers, travelClass, tripType ->
                        searchOrigin = from
                        searchDest = to
                        searchDate = date
                        searchPassengers = passengers
                        searchClass = travelClass
                        searchTripType = tripType
                        navController.navigate(Screen.FlightSearch.route)
                    }
                )
            }

            // Sub-screen: Flight Search
            composable(Screen.FlightSearch.route) {
                FlightSearchScreen(
                    repository = repository,
                    from = searchOrigin,
                    to = searchDest,
                    date = searchDate,
                    passengers = searchPassengers,
                    travelClass = searchClass,
                    tripType = searchTripType,
                    onNavigateBack = { navController.popBackStack() },
                    onFlightBooked = { flight ->
                        // Automatically can view in Wallet or Trips
                    }
                )
            }

            // Sub-screen: Map Discovery
            composable(Screen.MapDiscovery.route) {
                MapDiscoveryScreen(
                    repository = repository,
                    onNavigateBack = { navController.popBackStack() },
                    onPlanTripForLocation = { location ->
                        navController.navigate(Screen.BookingConfig.createRoute(location))
                    }
                )
            }

            // Sub-screen: Trip Timeline
            composable(
                route = Screen.TripTimeline.route,
                arguments = listOf(navArgument("tripId") { type = NavType.LongType })
            ) { backStackEntry ->
                val tripId = backStackEntry.arguments?.getLong("tripId") ?: 1L
                TripTimelineScreen(
                    tripId = tripId,
                    repository = repository,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToRentals = { navController.navigate(Screen.EVRental.route) },
                    onNavigateToHealthSafety = { navController.navigate(Screen.HealthSafety.route) }
                )
            }
            
            // Sidebar Screens
            composable(Screen.JharkhandGlance.route) {
                JharkhandGlanceScreen()
            }
            composable(Screen.Events.route) {
                EventsScreen()
            }
            composable(Screen.Hospitality.route) {
                HospitalityScreen()
            }
            composable(Screen.Souvenirs.route) {
                SouvenirsScreen()
            }
            composable(Screen.HeliTourism.route) {
                HeliTourismScreen()
            }
            composable(Screen.AudioGuide.route) {
                AudioGuideScreen()
            }
            composable(Screen.Share.route) {
                ShareScreen()
            }

            // Where to Go Discovery Screen
            composable(Screen.WhereToGo.route) {
                val whereToGoViewModel = androidx.lifecycle.viewmodel.compose.viewModel {
                    com.example.ui.discovery.WhereToGoViewModel(repository)
                }
                com.example.ui.discovery.WhereToGoScreen(
                    viewModel = whereToGoViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToDestinationDetail = { destId ->
                        navController.navigate(Screen.DestinationDetail.createRoute(destId))
                    }
                )
            }

            // Destination Detail Screen
            composable(
                route = Screen.DestinationDetail.route,
                arguments = listOf(navArgument("destinationId") { type = NavType.LongType })
            ) { backStackEntry ->
                val destId = backStackEntry.arguments?.getLong("destinationId") ?: 1L
                com.example.ui.discovery.DestinationDetailScreen(
                    destinationId = destId,
                    repository = repository,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToBooking = { destName ->
                        navController.navigate(Screen.BookingConfig.createRoute(destName))
                    },
                    onNavigateToAudioGuide = {
                        navController.navigate(Screen.AudioGuide.route)
                    },
                    onNavigateToDestination = { nextDestId ->
                        navController.navigate(Screen.DestinationDetail.createRoute(nextDestId))
                    },
                    onNavigateToRentals = {
                        navController.navigate(Screen.EVRental.route)
                    }
                )
            }

            // Rentals Discovery Screen
            composable(Screen.EVRental.route) {
                val evRentalViewModel = androidx.lifecycle.viewmodel.compose.viewModel {
                    com.example.ui.rentals.EVRentalViewModel(repository)
                }
                com.example.ui.rentals.EVRentalScreen(
                    viewModel = evRentalViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToDetail = { rentalId ->
                        navController.navigate(Screen.EVRentalDetail.createRoute(rentalId))
                    },
                    onNavigateToTimeline = { tripId ->
                        navController.navigate(Screen.TripTimeline.createRoute(tripId))
                    }
                )
            }

            // Rentals Detail Screen
            composable(
                route = Screen.EVRentalDetail.route,
                arguments = listOf(navArgument("rentalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val rentalId = backStackEntry.arguments?.getInt("rentalId") ?: 1
                val evRentalViewModel = androidx.lifecycle.viewmodel.compose.viewModel {
                    com.example.ui.rentals.EVRentalViewModel(repository)
                }
                com.example.ui.rentals.EVRentalDetailScreen(
                    rentalId = rentalId,
                    viewModel = evRentalViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToTimeline = { tripId ->
                        navController.navigate(Screen.TripTimeline.createRoute(tripId))
                    }
                )
            }
        }
    }
}
}

