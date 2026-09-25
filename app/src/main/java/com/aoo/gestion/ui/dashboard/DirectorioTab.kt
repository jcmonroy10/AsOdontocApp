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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.SpectralFamily

private data class DirectoryMember(val id: Int, val name: String, val specialty: String, val initials: String, val color: Color)

private val directoryMembers = listOf(
    DirectoryMember(1, "Dr. Manuel González", "Ortodoncia", "MG", Color(0xFF32306B)),
    DirectoryMember(2, "Dra. Carmen Ramírez", "Odontopediatría", "CR", Color(0xFF487624)),
    DirectoryMember(3, "Dr. José Portillo", "Endodoncia", "JP", Color(0xFF8A7A3A)),
    DirectoryMember(4, "Dra. Lourdes Acevedo", "Odontología General", "LA", Color(0xFF32306B))
)

@Composable
fun DirectorioTab(onOpenMember: (Int) -> Unit) {
    var query by remember { mutableStateOf("") }
    var favorites by remember { mutableStateOf(setOf<Int>()) }

    val filtered = directoryMembers.filter {
        query.isBlank() || it.name.contains(query, ignoreCase = true) || it.specialty.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Buscar por nombre o especialidad") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            filtered.forEach { member ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f).clickable { onOpenMember(member.id) }
                    ) {
                        Box(modifier = Modifier.size(42.dp).background(member.color, CircleShape), contentAlignment = Alignment.Center) {
                            Text(member.initials, color = Color.White, style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Text(member.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text(member.specialty, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    val isFavorite = member.id in favorites
                    IconButton(onClick = { favorites = if (isFavorite) favorites - member.id else favorites + member.id }) {
                        Icon(
                            if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) Color(0xFFD8CA8D) else MaterialTheme.colorScheme.outline
                        )
                    }
                }
                HorizontalDivider()
            }
        }
    }
}
