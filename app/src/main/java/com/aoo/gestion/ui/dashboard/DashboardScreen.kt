package com.aoo.gestion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
    var selectedTab by remember { mutableStateOf(AooTab.INICIO) }

    Scaffold(
        topBar = { DashboardHeader(selectedTab = selectedTab, onBack = { selectedTab = AooTab.INICIO }) },
        bottomBar = {
            DashboardBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                AooTab.INICIO -> InicioTab(
                    onNavigate = { selectedTab = it },
                    onOpenPagos = { selectedTab = AooTab.PAGOS }
                )
                AooTab.EVENTOS -> PlaceholderTab("El catálogo de eventos se arma en el siguiente paso.")
                AooTab.DIPLOMAS -> PlaceholderTab("Tus diplomas se arman en el siguiente paso.")
                AooTab.PAGOS -> PlaceholderTab("El detalle de pagos y facturas se arma en el siguiente paso.")
                AooTab.DIRECTORIO -> PlaceholderTab("El directorio de socios se arma en el siguiente paso.")
                AooTab.PERFIL -> PlaceholderTab("Tu perfil se arma en el siguiente paso.")
            }
        }
    }
}

@Composable
private fun DashboardHeader(selectedTab: AooTab, onBack: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            if (selectedTab == AooTab.INICIO) {
                InicioHeader()
            } else {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
                    }
                    Text(
                        text = selectedTab.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardBottomBar(selectedTab: AooTab, onTabSelected: (AooTab) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == AooTab.INICIO,
            onClick = { onTabSelected(AooTab.INICIO) },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = selectedTab == AooTab.EVENTOS,
            onClick = { onTabSelected(AooTab.EVENTOS) },
            icon = { Icon(Icons.Filled.Event, contentDescription = null) },
            label = { Text("Eventos") }
        )
        NavigationBarItem(
            selected = selectedTab == AooTab.DIPLOMAS,
            onClick = { onTabSelected(AooTab.DIPLOMAS) },
            icon = { Icon(Icons.Filled.CardMembership, contentDescription = null) },
            label = { Text("Diplomas") }
        )
        NavigationBarItem(
            selected = selectedTab == AooTab.PAGOS,
            onClick = { onTabSelected(AooTab.PAGOS) },
            icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null) },
            label = { Text("Pagos") }
        )
        NavigationBarItem(
            selected = selectedTab == AooTab.PERFIL,
            onClick = { onTabSelected(AooTab.PERFIL) },
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Perfil") }
        )
    }
}

@Composable
private fun PlaceholderTab(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}
