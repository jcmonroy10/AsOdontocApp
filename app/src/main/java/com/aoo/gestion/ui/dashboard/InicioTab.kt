package com.aoo.gestion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class NotificationItem(val id: Int, val text: String, val timeAgo: String)

private data class UpcomingEvent(
    val id: Int,
    val dateLabel: String,
    val title: String,
    val place: String,
    val gradient: Brush
)

@Composable
fun InicioHeader() {
    var showNotifications by remember { mutableStateOf(false) }
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationItem(1, "Tu cuota de octubre vence en 5 días.", "Hace 2 horas"),
                NotificationItem(2, "Tu diploma de «Bioseguridad Clínica» ya está disponible.", "Ayer"),
                NotificationItem(3, "Nuevo evento disponible: Congreso Gremial 2026.", "Hace 3 días")
            )
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("LA", color = MaterialTheme.colorScheme.onPrimary, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
            }
            Column {
                Text("Bienvenido de nuevo", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Dra. Lourdes Acevedo", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            }
        }

        Box {
            IconButton(
                onClick = { showNotifications = true },
                modifier = Modifier
                    .size(42.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            ) {
                Box {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = MaterialTheme.colorScheme.primary)
                    if (notifications.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .background(Color(0xFFD8CA8D), CircleShape)
                        )
                    }
                }
            }

            DropdownMenu(expanded = showNotifications, onDismissRequest = { showNotifications = false }) {
                Column(modifier = Modifier.width(280.dp).padding(8.dp)) {
                    Text("Notificaciones", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))
                    HorizontalDivider()
                    if (notifications.isEmpty()) {
                        Text(
                            "No tienes notificaciones nuevas.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    notifications.forEach { notif ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(notif.text, style = MaterialTheme.typography.bodyMedium)
                                Text(notif.timeAgo, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = { notifications = notifications.filterNot { it.id == notif.id } }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Filled.Close, contentDescription = "Descartar", modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InicioTab(
    onNavigate: (AooTab) -> Unit,
    onPayCuota: () -> Unit,
    onOpenEventDetail: (Int) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    var event1Confirmed by remember { mutableStateOf(false) }
    var event2Confirmed by remember { mutableStateOf(false) }

    val events = remember {
        listOf(
            UpcomingEvent(1, "18 SEP · 4:00 PM", "Actualización en Endodoncia", "Sede AOO · Santa Ana", Brush.linearGradient(listOf(Color(0xFFD8CA8D), Color(0xFFB0954F)))),
            UpcomingEvent(2, "02 OCT · 9:00 AM", "Congreso Gremial 2026", "Hotel Sahara · Santa Ana", Brush.linearGradient(listOf(Color(0xFF32306B), Color(0xFF4A4869))))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        // Estado de membresía
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Estado de membresía", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .background(AooSuccess.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Activo", style = MaterialTheme.typography.labelSmall, color = AooSuccess, fontWeight = FontWeight.Bold)
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                    Column {
                        Text("Cuota de octubre", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$15.00", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Vence", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("05 nov 2026", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                    }
                }

                Button(
                    onClick = onPayCuota,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp)
                ) {
                    Text("Pagar cuota", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Accesos rápidos
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Accesos rápidos", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                QuickAccessTile("Eventos", Icons.Filled.Event, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) { onNavigate(AooTab.EVENTOS) }
                QuickAccessTile("Diplomas", Icons.Filled.CardMembership, Color(0xFFD8CA8D).copy(alpha = 0.28f)) { onNavigate(AooTab.DIPLOMAS) }
                QuickAccessTile("Pagos", Icons.AutoMirrored.Filled.ReceiptLong, AooSuccess.copy(alpha = 0.1f)) { onNavigate(AooTab.PAGOS) }
                QuickAccessTile("Directorio", Icons.Filled.Groups, MaterialTheme.colorScheme.surfaceVariant) { onNavigate(AooTab.DIRECTORIO) }
            }
        }

        // Próximos eventos
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Próximos eventos", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(
                    "Ver todos",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                events.forEach { event ->
                    val confirmed = if (event.id == 1) event1Confirmed else event2Confirmed
                    EventPreviewCard(
                        event = event,
                        confirmed = confirmed,
                        onToggleConfirm = {
                            if (event.id == 1) event1Confirmed = !event1Confirmed else event2Confirmed = !event2Confirmed
                        },
                        onOpenDetail = { onOpenEventDetail(event.id) }
                    )
                }
            }
        }

        // Contáctanos
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 24.dp)) {
            Text("Contáctanos", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                ContactTile(
                    modifier = Modifier.weight(1f),
                    label = "Instagram",
                    detail = "@aoo.elsalvador",
                    iconColor = Color(0xFFEE2A7B)
                ) { uriHandler.openUri("https://instagram.com/aoo.elsalvador") }
                ContactTile(
                    modifier = Modifier.weight(1f),
                    label = "WhatsApp",
                    detail = "Dra. Lourdes Acevedo",
                    iconColor = Color(0xFF25D366)
                ) { uriHandler.openUri("https://wa.me/50370000000") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                ContactTile(
                    modifier = Modifier.weight(1f),
                    label = "Facebook",
                    detail = "AOO El Salvador",
                    iconColor = Color(0xFF1877F2)
                ) { uriHandler.openUri("https://facebook.com/aoo.elsalvador") }
                ContactTile(
                    modifier = Modifier.weight(1f),
                    label = "Sitio web",
                    detail = "Próximamente",
                    iconColor = MaterialTheme.colorScheme.primary,
                    icon = Icons.Filled.Language,
                    onClick = null
                )
            }
        }
    }
}

@Composable
private fun QuickAccessTile(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, bg: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButton(onClick = onClick, modifier = Modifier.size(56.dp).background(bg, RoundedCornerShape(16.dp))) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onSurface)
        }
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun EventPreviewCard(event: UpcomingEvent, confirmed: Boolean, onToggleConfirm: () -> Unit, onOpenDetail: () -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.width(280.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(event.gradient),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Event, contentDescription = null, tint = Color.White.copy(alpha = 0.92f), modifier = Modifier.size(30.dp))
        }
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(event.dateLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
            Text(event.title, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
            Text(event.place, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = onOpenDetail,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(34.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text("Detalle", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onToggleConfirm,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (confirmed) AooSuccess else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1.5f).height(34.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    if (confirmed) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        if (confirmed) "Confirmado" else "Confirmar asistencia",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactTile(
    modifier: Modifier = Modifier,
    label: String,
    detail: String,
    iconColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Filled.Language,
    onClick: (() -> Unit)?
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier.clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(detail, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (onClick != null) {
                Text(
                    "Abrir",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = iconColor,
                    modifier = Modifier
                        .background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                )
            }
        }
    }
}
