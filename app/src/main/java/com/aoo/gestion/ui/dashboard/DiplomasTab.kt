package com.aoo.gestion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class DiplomaRow(val id: Int, val title: String, val issuedLabel: String)

private val diplomaRows = listOf(
    DiplomaRow(1, "Bioseguridad Clínica", "Emitido el 14 ago 2026"),
    DiplomaRow(2, "Jornada de Ética Profesional", "Emitido el 30 jun 2026"),
    DiplomaRow(3, "Congreso Gremial 2025", "Emitido el 03 oct 2025")
)

@Composable
fun DiplomasTab(onOpenDiploma: (Int) -> Unit) {
    var downloaded by remember { mutableStateOf(setOf<Int>()) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        diplomaRows.forEach { diploma ->
            Card(shape = RoundedCornerShape(16.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth().padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.weight(1f).clickable { onOpenDiploma(diploma.id) }
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp).background(androidx.compose.ui.graphics.Color(0xFFD8CA8D).copy(alpha = 0.28f), RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.CardMembership, contentDescription = null, tint = androidx.compose.ui.graphics.Color(0xFF8A7A3A))
                        }
                        Column {
                            Text(diploma.title, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                            Text("${diploma.issuedLabel} · Ver diploma", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    val isDownloaded = diploma.id in downloaded
                    IconButton(
                        onClick = { downloaded = downloaded + diploma.id },
                        modifier = Modifier
                            .size(38.dp)
                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                    ) {
                        if (isDownloaded) {
                            Icon(Icons.Filled.Check, contentDescription = "Descargado", tint = AooSuccess, modifier = Modifier.size(16.dp))
                        } else {
                            Icon(Icons.Filled.Download, contentDescription = "Descargar", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}
