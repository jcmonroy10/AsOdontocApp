package com.aoo.gestion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aoo.gestion.ui.dashboard.DashboardScreen
import com.aoo.gestion.ui.diploma.DiplomaPreviewScreen
import com.aoo.gestion.ui.eventdetail.EventDetailScreen
import com.aoo.gestion.ui.invoice.InvoiceScreen
import com.aoo.gestion.ui.login.LoginScreen
import com.aoo.gestion.ui.member.MemberProfileScreen
import com.aoo.gestion.ui.pasarela.PasarelaScreen
import com.aoo.gestion.ui.profile.ChangePasswordScreen
import com.aoo.gestion.ui.profile.EditProfileScreen
import com.aoo.gestion.ui.profile.HelpSupportScreen
import com.aoo.gestion.ui.profile.NotifSettingsScreen
import com.aoo.gestion.ui.splash.SplashScreen
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun AooNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AooDestinations.SPLASH) {
        composable(AooDestinations.SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(AooDestinations.LOGIN) {
                        popUpTo(AooDestinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(AooDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AooDestinations.DASHBOARD) {
                        popUpTo(AooDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(AooDestinations.DASHBOARD) {
            DashboardScreen(
                onOpenEventDetail = { id -> navController.navigate(AooDestinations.eventDetail(id)) },
                onOpenDiplomaPreview = { id -> navController.navigate(AooDestinations.diplomaPreview(id)) },
                onOpenInvoice = { key -> navController.navigate(AooDestinations.invoice(key)) },
                onOpenMember = { id -> navController.navigate(AooDestinations.member(id)) },
                onOpenEditProfile = { navController.navigate(AooDestinations.EDIT_PROFILE) },
                onOpenChangePassword = { navController.navigate(AooDestinations.CHANGE_PASSWORD) },
                onOpenNotifSettings = { navController.navigate(AooDestinations.NOTIF_SETTINGS) },
                onOpenHelpSupport = { navController.navigate(AooDestinations.HELP_SUPPORT) },
                onPay = { concepto, monto ->
                    val encoded = URLEncoder.encode(concepto, "UTF-8")
                    navController.navigate("${AooDestinations.PASARELA}?concepto=$encoded&monto=$monto")
                },
                onLogout = {
                    navController.navigate(AooDestinations.LOGIN) {
                        popUpTo(AooDestinations.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = AooDestinations.EVENT_DETAIL,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 1
            EventDetailScreen(eventId = eventId, onBack = { navController.popBackStack() })
        }
        composable(
            route = "${AooDestinations.PASARELA}?concepto={concepto}&monto={monto}",
            arguments = listOf(
                navArgument("concepto") { type = NavType.StringType; defaultValue = "Cuota de octubre" },
                navArgument("monto") { type = NavType.StringType; defaultValue = "15.00" }
            )
        ) { backStackEntry ->
            val concepto = backStackEntry.arguments?.getString("concepto")?.let { URLDecoder.decode(it, "UTF-8") } ?: "Cuota de octubre"
            val monto = backStackEntry.arguments?.getString("monto") ?: "15.00"
            PasarelaScreen(
                concepto = concepto,
                monto = monto,
                onCancel = { navController.popBackStack() },
                onFinish = { navController.popBackStack() }
            )
        }
        composable(
            route = AooDestinations.DIPLOMA_PREVIEW,
            arguments = listOf(navArgument("diplomaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val diplomaId = backStackEntry.arguments?.getInt("diplomaId") ?: 1
            DiplomaPreviewScreen(diplomaId = diplomaId, onBack = { navController.popBackStack() })
        }
        composable(
            route = AooDestinations.INVOICE,
            arguments = listOf(navArgument("invoiceKey") { type = NavType.StringType })
        ) { backStackEntry ->
            val invoiceKey = backStackEntry.arguments?.getString("invoiceKey") ?: "oct"
            InvoiceScreen(invoiceKey = invoiceKey, onBack = { navController.popBackStack() })
        }
        composable(
            route = AooDestinations.MEMBER,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: 1
            MemberProfileScreen(memberId = memberId, onBack = { navController.popBackStack() })
        }
        composable(AooDestinations.EDIT_PROFILE) {
            EditProfileScreen(onBack = { navController.popBackStack() })
        }
        composable(AooDestinations.CHANGE_PASSWORD) {
            ChangePasswordScreen(onBack = { navController.popBackStack() })
        }
        composable(AooDestinations.NOTIF_SETTINGS) {
            NotifSettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(AooDestinations.HELP_SUPPORT) {
            HelpSupportScreen(onBack = { navController.popBackStack() })
        }
    }
}
