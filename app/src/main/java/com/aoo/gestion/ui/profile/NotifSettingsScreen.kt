package com.aoo.gestion.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import com.aoo.gestion.ui.theme.SpectralFamily

@Composable
fun NotifSettingsScreen(onBack: () -> Unit) {
    var eventos by remember { mutableStateOf(true) }
    var pagos by remember { mutableStateOf(true) }
    var diplomas by remember { mutableStateOf(true) }
    var noticias by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(bottom = 20.dp)) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }
            Text("Notificaciones", style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
        }

        Text(
            "Elige qué notificaciones quieres recibir.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        NotifToggleRow("Eventos y capacitaciones", "Avisos de nuevos eventos e inscripciones", eventos) { eventos = it }
        HorizontalDivider()
        NotifToggleRow("Pagos y facturación", "Recordatorios de cuotas pendientes", pagos) { pagos = it }
        HorizontalDivider()
        NotifToggleRow("Recordatorios de diplomas", "Cuando un nuevo diploma esté disponible", diplomas) { diplomas = it }
        HorizontalDivider()
        NotifToggleRow("Noticias del gremio", "Comunicados generales de la asociación", noticias) { noticias = it }
    }
}

@Composable
private fun NotifToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
