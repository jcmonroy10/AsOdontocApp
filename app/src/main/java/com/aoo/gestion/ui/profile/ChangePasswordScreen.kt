package com.aoo.gestion.ui.profile

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class PwStatus { IDLE, LOADING, SUCCESS }

@Composable
fun ChangePasswordScreen(onBack: () -> Unit) {
    var current by remember { mutableStateOf("") }
    var newPw by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(PwStatus.IDLE) }
    val scope = rememberCoroutineScope()

    fun submit() {
        error = when {
            current.isBlank() || newPw.isBlank() || confirm.isBlank() -> "Completa todos los campos."
            newPw.length < 6 -> "La nueva contraseña debe tener al menos 6 caracteres."
            newPw != confirm -> "Las contraseñas nuevas no coinciden."
            else -> ""
        }
        if (error.isEmpty()) {
            status = PwStatus.LOADING
            scope.launch {
                delay(1100)
                status = PwStatus.SUCCESS
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(bottom = 20.dp)) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }
            Text("Cambiar contraseña", style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
        }

        if (status == PwStatus.SUCCESS) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(top = 40.dp)) {
                Box(modifier = Modifier.size(56.dp).background(AooSuccess.copy(alpha = 0.1f), RoundedCornerShape(50)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.size(26.dp))
                }
                Text("Tu contraseña se actualizó correctamente.", style = MaterialTheme.typography.bodyMedium, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Button(onClick = onBack, shape = RoundedCornerShape(12.dp), modifier = Modifier.height(48.dp)) {
                    Text("Volver a mi perfil", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = current, onValueChange = { current = it }, label = { Text("Contraseña actual") },
                    singleLine = true, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newPw, onValueChange = { newPw = it }, label = { Text("Nueva contraseña") },
                    singleLine = true, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = confirm, onValueChange = { confirm = it }, label = { Text("Confirmar nueva contraseña") },
                    singleLine = true, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth()
                )

                if (error.isNotEmpty()) {
                    Text(
                        error,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    )
                }

                Button(
                    onClick = { submit() },
                    enabled = status != PwStatus.LOADING,
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    AnimatedContent(targetState = status, label = "pwState") { current2 ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (current2 == PwStatus.LOADING) {
                                CircularProgressIndicator(modifier = Modifier.size(19.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 3.dp)
                                Text("Actualizando…", fontWeight = FontWeight.Bold)
                            } else {
                                Text("Actualizar contraseña", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
