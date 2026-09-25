package com.aoo.gestion.ui.pasarela

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class PasarelaStep { FORM, PROCESANDO, APROBADO }

private val wompiGreen = Color(0xFF00C389)
private val wompiTeal = Color(0xFF076B63)

@Composable
fun PasarelaScreen(concepto: String, monto: String, onCancel: () -> Unit, onFinish: () -> Unit) {
    var step by remember { mutableStateOf(PasarelaStep.FORM) }
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExp by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun submitPago() {
        step = PasarelaStep.PROCESANDO
        scope.launch {
            delay(1400)
            step = PasarelaStep.APROBADO
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 36.dp)) {
        when (step) {
            PasarelaStep.FORM -> PasarelaForm(
                concepto = concepto,
                monto = monto,
                cardName = cardName,
                onCardNameChange = { cardName = it },
                cardNumber = cardNumber,
                onCardNumberChange = { cardNumber = it },
                cardExp = cardExp,
                onCardExpChange = { cardExp = it },
                cardCvv = cardCvv,
                onCardCvvChange = { cardCvv = it },
                onCancel = onCancel,
                onSubmit = { submitPago() }
            )
            PasarelaStep.PROCESANDO -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    CircularProgressIndicator(color = wompiGreen, strokeWidth = 3.dp)
                    Text("Procesando pago con wompi…", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
            }
            PasarelaStep.APROBADO -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.size(64.dp).background(AooSuccess.copy(alpha = 0.12f), RoundedCornerShape(50)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.size(30.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Pago aprobado", style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                        Text("Se cobraron $$monto a tu tarjeta.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Button(onClick = onFinish, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().height(50.dp)) {
                        Text("Ver mis comprobantes", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun PasarelaForm(
    concepto: String,
    monto: String,
    cardName: String,
    onCardNameChange: (String) -> Unit,
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    cardExp: String,
    onCardExpChange: (String) -> Unit,
    cardCvv: String,
    onCardCvvChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
            IconButton(onClick = onCancel, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }
            Text("wompi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = wompiGreen)
        }

        Column {
            Text("Pago seguro", style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
            Text("Completa los datos de tu tarjeta para continuar.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        // Tarjeta visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(listOf(wompiGreen, Color(0xFF00997A), wompiTeal)), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.CreditCard, contentDescription = null, tint = Color.White.copy(alpha = 0.85f))
                    Text("wompi", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold, color = Color.White.copy(alpha = 0.92f))
                }
                Column {
                    val masked = if (cardNumber.isNotBlank()) "••••  ••••  ••••  ${cardNumber.takeLast(4).padStart(4, '•')}" else "••••  ••••  ••••  ••••"
                    Text(masked, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
                        Text(cardName.ifBlank { "NOMBRE APELLIDO" }.uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.85f))
                        Text(cardExp.ifBlank { "MM/AA" }, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }

        Card(shape = RoundedCornerShape(14.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(concepto, style = MaterialTheme.typography.bodyMedium)
                    Text("$$monto", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
                androidx.compose.material3.HorizontalDivider()
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Total a pagar", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    Text("$$monto", style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold, color = wompiTeal)
                }
            }
        }

        OutlinedTextField(value = cardName, onValueChange = onCardNameChange, label = { Text("Nombre en la tarjeta") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = cardNumber, onValueChange = onCardNumberChange, label = { Text("Número de tarjeta") }, placeholder = { Text("0000 0000 0000 0000") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = cardExp, onValueChange = onCardExpChange, label = { Text("Vencimiento") }, placeholder = { Text("MM/AA") }, singleLine = true, modifier = Modifier.weight(1f))
            OutlinedTextField(value = cardCvv, onValueChange = onCardCvvChange, label = { Text("CVV") }, placeholder = { Text("123") }, singleLine = true, modifier = Modifier.weight(1f))
        }

        val formValid = cardName.isNotBlank() && cardNumber.isNotBlank() && cardExp.isNotBlank() && cardCvv.isNotBlank()
        Button(
            onClick = onSubmit,
            enabled = formValid,
            shape = RoundedCornerShape(13.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = wompiGreen),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Pagar $$monto", fontWeight = FontWeight.Bold)
        }

        Text(
            "Procesado de forma segura por wompi",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
