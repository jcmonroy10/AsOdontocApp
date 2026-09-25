package com.aoo.gestion.ui.diploma

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.R
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class DiplomaInfo(val id: Int, val title: String, val issuedLabel: String)

private val diplomas = listOf(
    DiplomaInfo(1, "Bioseguridad Clínica", "Emitido el 14 de agosto de 2026 · Santa Ana, El Salvador"),
    DiplomaInfo(2, "Jornada de Ética Profesional", "Emitido el 30 de junio de 2026 · Santa Ana, El Salvador"),
    DiplomaInfo(3, "Congreso Gremial 2025", "Emitido el 03 de octubre de 2025 · Santa Ana, El Salvador")
)

@Composable
fun DiplomaPreviewScreen(diplomaId: Int, onBack: () -> Unit) {
    val diploma = diplomas.firstOrNull { it.id == diplomaId } ?: diplomas.first()
    var downloaded by rememberSaveable(diplomaId) { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
        }

        Card(
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(2.dp, androidx.compose.ui.graphics.Color(0xFFD8CA8D))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(6.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 26.dp, horizontal = 22.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.logo_aoo), contentDescription = null, modifier = Modifier.size(46.dp))
                Text(
                    "ASOCIACIÓN ODONTOLÓGICA DE OCCIDENTE",
                    style = MaterialTheme.typography.labelSmall,
                    color = androidx.compose.ui.graphics.Color(0xFF8A7A3A),
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp)
                )

                androidx.compose.foundation.layout.Box(modifier = Modifier.padding(vertical = 10.dp).size(width = 40.dp, height = 2.dp).background(androidx.compose.ui.graphics.Color(0xFFD8CA8D)))

                Text("Diploma de Participación", style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)

                Text("Se otorga el presente reconocimiento a", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 14.dp))
                Text("Dra. Lourdes Acevedo", style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)

                Text("por su participación en", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 14.dp))
                Text(diploma.title, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Text(diploma.issuedLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 8.dp))

                androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(28.dp), modifier = Modifier.padding(top = 26.dp)) {
                    SignatureLine("Presidencia AOO")
                    SignatureLine("Secretaría")
                }
            }
        }

        OutlinedButton(
            onClick = { downloaded = true },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            if (downloaded) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.size(16.dp))
                Text(" Descargado", color = AooSuccess, fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Text(" Descargar PDF", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SignatureLine(label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        androidx.compose.foundation.layout.Box(modifier = Modifier.width(84.dp).height(1.dp).background(MaterialTheme.colorScheme.outlineVariant))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
