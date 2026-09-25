package com.aoo.gestion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class EventoProximo(
    val id: Int,
    val dateLabel: String,
    val title: String,
    val place: String,
    val badge: String,
    val badgeColor: Color,
    val gradient: Brush,
    var confirmed: Boolean
)

private data class EventoPasado(
    val id: Int,
    val dateLabel: String,
    val title: String,
    val place: String,
    val gradient: Brush
)

private enum class EventFilter { PROXIMOS, PASADOS }

@Composable
fun EventosTab(onOpenEventDetail: (Int) -> Unit, onOpenDiploma: (Int) -> Unit) {
    var filter by remember { mutableStateOf(EventFilter.PROXIMOS) }

    val proximos = remember {
        mutableStateOf(
            listOf(
                EventoProximo(1, "18 SEP · 4:00 PM", "Actualización en Endodoncia", "Sede AOO · Santa Ana", "Gratis socios", AooSuccess, Brush.linearGradient(listOf(Color(0xFFD8CA8D), Color(0xFFB0954F))), false),
                EventoProximo(2, "02 OCT · 9:00 AM", "Congreso Gremial 2026", "Hotel Sahara · Santa Ana", "$10 externos", Color(0xFF32306B), Brush.linearGradient(listOf(Color(0xFF32306B), Color(0xFF4A4869))), false),
                EventoProximo(3, "27 OCT · 6:00 PM", "Taller de Rehabilitación Oral", "Sede AOO · Santa Ana", "Gratis socios", AooSuccess, Brush.linearGradient(listOf(Color(0xFF487624), Color(0xFF355A1A))), false)
            )
        )
    }

    val pasados = remember {
        listOf(
            EventoPasado(1, "14 AGO · 3:00 PM", "Bioseguridad Clínica", "Sede AOO · Santa Ana", Brush.linearGradient(listOf(Color(0xFF487624), Color(0xFF355A1A)))),
            EventoPasado(2, "30 JUN · 5:00 PM", "Jornada de Ética Profesional", "Sede AOO · Santa Ana", Brush.linearGradient(listOf(Color(0xFFD8CA8D), Color(0xFFB0954F))))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SegmentButton("Próximos", selected = filter == EventFilter.PROXIMOS, modifier = Modifier.weight(1f)) { filter = EventFilter.PROXIMOS }
            SegmentButton("Pasados", selected = filter == EventFilter.PASADOS, modifier = Modifier.weight(1f)) { filter = EventFilter.PASADOS }
        }

        if (filter == EventFilter.PROXIMOS) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(bottom = 24.dp)) {
                proximos.value.forEachIndexed { index, evento ->
                    EventoProximoCard(
                        evento = evento,
                        onToggleConfirm = {
                            proximos.value = proximos.value.toMutableList().also { list ->
                                list[index] = evento.copy(confirmed = !evento.confirmed)
                            }
                        },
                        onOpenDetail = { onOpenEventDetail(evento.id) }
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(bottom = 24.dp)) {
                pasados.forEach { evento -> EventoPasadoCard(evento, onOpenDiploma = { onOpenDiploma(evento.id) }) }
            }
        }
    }
}

@Composable
private fun SegmentButton(label: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(9.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = null,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun EventoProximoCard(evento: EventoProximo, onToggleConfirm: () -> Unit, onOpenDetail: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .background(evento.gradient),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Event, contentDescription = null, tint = Color.White.copy(alpha = 0.92f), modifier = Modifier.height(44.dp))
        }
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(evento.dateLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
                    Text(evento.title, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                    Text("${evento.place} · Ver detalle", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(
                    modifier = Modifier
                        .background(evento.badgeColor.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
                        .padding(horizontal = 9.dp, vertical = 4.dp)
                ) {
                    Text(evento.badge, style = MaterialTheme.typography.labelSmall, color = evento.badgeColor, fontWeight = FontWeight.Bold)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = onOpenDetail,
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) {
                    Text("Detalle", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onToggleConfirm,
                    shape = RoundedCornerShape(11.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (evento.confirmed) AooSuccess else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1.5f).height(44.dp)
                ) {
                    if (evento.confirmed) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.height(14.dp))
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(if (evento.confirmed) "Confirmado" else "Confirmar asistencia", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun EventoPasadoCard(evento: EventoPasado, onOpenDiploma: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .background(evento.gradient),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.WorkspacePremium, contentDescription = null, tint = Color.White.copy(alpha = 0.92f), modifier = Modifier.height(42.dp))
        }
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Column {
                Text(evento.dateLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
                Text(evento.title, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                Text(evento.place, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.height(13.dp))
                Text("Asististe", style = MaterialTheme.typography.labelSmall, color = AooSuccess, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = onOpenDiploma,
                shape = RoundedCornerShape(11.dp),
                modifier = Modifier.fillMaxWidth().height(44.dp)
            ) {
                Text("Ver diploma", fontWeight = FontWeight.Bold)
            }
        }
    }
}
