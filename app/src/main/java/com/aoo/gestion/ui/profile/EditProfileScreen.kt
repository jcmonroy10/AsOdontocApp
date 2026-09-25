package com.aoo.gestion.ui.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class SaveStatus { IDLE, LOADING, SUCCESS }

@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("Dra. Lourdes Acevedo") }
    var specialty by remember { mutableStateOf("Odontología General") }
    var email by remember { mutableStateOf("ahelthysmiledentist2016@gmail.com") }
    var phone by remember { mutableStateOf("7768-4888") }
    var status by remember { mutableStateOf(SaveStatus.IDLE) }
    val scope = rememberCoroutineScope()

    fun submit() {
        status = SaveStatus.LOADING
        scope.launch {
            delay(900)
            status = SaveStatus.SUCCESS
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(bottom = 20.dp)) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }
            Text("Editar información", style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.weight(1f)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre completo") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = specialty, onValueChange = { specialty = it }, label = { Text("Especialidad") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo electrónico") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        }

        Button(
            onClick = { if (status == SaveStatus.IDLE) submit() },
            enabled = status != SaveStatus.LOADING,
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (status == SaveStatus.SUCCESS) AooSuccess else MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            AnimatedContent(targetState = status, label = "saveProfileState") { current ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    when (current) {
                        SaveStatus.IDLE -> Text("Guardar cambios", fontWeight = FontWeight.Bold)
                        SaveStatus.LOADING -> {
                            CircularProgressIndicator(modifier = Modifier.size(19.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 3.dp)
                            Text("Guardando…", fontWeight = FontWeight.Bold)
                        }
                        SaveStatus.SUCCESS -> {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                            Text("Guardado", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
