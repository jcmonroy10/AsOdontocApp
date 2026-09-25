package com.aoo.gestion.ui.eventdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class EventDetailInfo(
    val id: Int,
    val stripColor: Color,
    val dateLabel: String,
    val title: String,
    val place: String,
    val badge: String,
    val badgeColor: Color,
    val about: String,
    val details: List<Pair<String, String>>
)

private val eventDetails = listOf(
    EventDetailInfo(
        1, Color(0xFFD8CA8D), "18 SEP · 4:00 PM", "Actualización en Endodoncia", "Sede AOO · Santa Ana",
        "Gratis socios", AooSuccess,
        "Sesión práctica sobre técnicas actualizadas de endodoncia rotatoria y manejo de conductos complejos, a cargo de especialistas invitados.",
        listOf("Ponente" to "Dr. Ricardo Fuentes", "Cupo" to "22 de 40 disponibles", "Requisitos" to "Gabacha e instrumental básico")
    ),
    EventDetailInfo(
        2, Color(0xFF32306B), "02 OCT · 9:00 AM", "Congreso Gremial 2026", "Hotel Sahara · Santa Ana",
        "$10 externos", Color(0xFF32306B),
        "Congreso anual de la Asociación Odontológica de Occidente con conferencias magistrales, talleres y espacio de networking gremial.",
        listOf("Ponentes" to "Invitados nacionales e internacionales", "Cupo" to "64 de 150 disponibles", "Incluye" to "Almuerzo y materiales")
    ),
    EventDetailInfo(
        3, Color(0xFF487624), "27 OCT · 6:00 PM", "Taller de Rehabilitación Oral", "Sede AOO · Santa Ana",
        "Gratis socios", AooSuccess,
        "Taller teórico-práctico sobre planificación de casos de rehabilitación oral compleja y soluciones protésicas.",
        listOf("Ponente" to "Dra. Karla Meza", "Cupo" to "12 de 30 disponibles", "Requisitos" to "Llegar 15 min antes")
    )
)

@Composable
fun EventDetailScreen(eventId: Int, onBack: () -> Unit) {
    val event = eventDetails.firstOrNull { it.id == eventId } ?: eventDetails.first()
    var confirmed by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth().height(10.dp).background(event.stripColor))

        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(event.dateLabel, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Box(modifier = Modifier.background(event.badgeColor.copy(alpha = 0.1f), RoundedCornerShape(100.dp)).padding(horizontal = 9.dp, vertical = 4.dp)) {
                        Text(event.badge, style = MaterialTheme.typography.labelSmall, color = event.badgeColor, fontWeight = FontWeight.Bold)
                    }
                }
                Text(event.title, style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                Text(event.place, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Sobre el evento", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(event.about, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Card(shape = RoundedCornerShape(14.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    event.details.forEach { (label, value) ->
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            if (confirmed) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = AooSuccess.copy(alpha = 0.06f)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AooSuccess.copy(alpha = 0.35f))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(16.dp)) {
                        Box(modifier = Modifier.size(34.dp).background(AooSuccess.copy(alpha = 0.15f), RoundedCornerShape(50)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.size(16.dp))
                        }
                        Column {
                            Text("Asistencia confirmada", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            Text("$name · $phone", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            } else {
                Card(shape = RoundedCornerShape(14.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Formulario de asistencia", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nombre completo") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Teléfono de contacto") },
                            placeholder = { Text("0000-0000") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = { confirmed = true },
                            enabled = name.isNotBlank() && phone.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Confirmar asistencia", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
