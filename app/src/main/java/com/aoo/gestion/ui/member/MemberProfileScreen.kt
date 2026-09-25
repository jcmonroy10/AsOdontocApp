package com.aoo.gestion.ui.member

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class MemberInfo(
    val id: Int,
    val name: String,
    val specialty: String,
    val initials: String,
    val color: Color,
    val email: String,
    val phone: String,
    val city: String
)

private val members = listOf(
    MemberInfo(1, "Dr. Manuel González", "Ortodoncia", "MG", Color(0xFF32306B), "manuel.gonzalez@aoo.org", "7123-4567", "Santa Ana"),
    MemberInfo(2, "Dra. Carmen Ramírez", "Odontopediatría", "CR", Color(0xFF487624), "carmen.ramirez@aoo.org", "7234-5678", "Santa Ana"),
    MemberInfo(3, "Dr. José Portillo", "Endodoncia", "JP", Color(0xFF8A7A3A), "jose.portillo@aoo.org", "7345-6789", "Santa Ana"),
    MemberInfo(4, "Dra. Lourdes Acevedo", "Odontología General", "LA", Color(0xFF32306B), "ahelthysmiledentist2016@gmail.com", "7768-4888", "Santa Ana")
)

@Composable
fun MemberProfileScreen(memberId: Int, onBack: () -> Unit) {
    val member = members.firstOrNull { it.id == memberId } ?: members.first()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(22.dp)) {
        IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.size(76.dp).background(member.color, CircleShape), contentAlignment = Alignment.Center) {
                Text(member.initials, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(member.name, style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                Text(member.specialty, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Box(modifier = Modifier.background(AooSuccess.copy(alpha = 0.1f), RoundedCornerShape(100.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text("Socio activo", style = MaterialTheme.typography.labelSmall, color = AooSuccess, fontWeight = FontWeight.Bold)
            }
        }

        Column {
            Text("Información de contacto", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
            ContactRow(Icons.Filled.Email, "Correo", member.email)
            HorizontalDivider()
            ContactRow(Icons.Filled.Phone, "Teléfono", member.phone)
            HorizontalDivider()
            ContactRow(Icons.Filled.LocationOn, "Sede", member.city)
        }
    }
}

@Composable
private fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Box(modifier = Modifier.size(34.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
        }
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}
