package com.aoo.gestion.ui.invoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aoo.gestion.R
import com.aoo.gestion.ui.theme.AooSuccess
import com.aoo.gestion.ui.theme.SpectralFamily

private data class InvoiceInfo(val key: String, val concepto: String, val monto: String, val fecha: String, val folio: String)

private val invoices = listOf(
    InvoiceInfo("oct", "Cuota de octubre", "15.00", "06 sep 2026", "DTE-0431"),
    InvoiceInfo("congreso26", "Inscripción · Congreso Gremial 2026", "25.00", "06 sep 2026", "DTE-0432"),
    InvoiceInfo("sep", "Cuota de septiembre", "15.00", "05 sep 2026", "DTE-0429"),
    InvoiceInfo("congreso25", "Inscripción · Congreso Gremial 2025", "10.00", "03 oct 2025", "DTE-0311"),
    InvoiceInfo("ago", "Cuota de agosto", "15.00", "04 ago 2026", "DTE-0402"),
    InvoiceInfo("jul", "Cuota de julio", "15.00", "03 jul 2026", "DTE-0378")
)

@Composable
fun InvoiceScreen(invoiceKey: String, onBack: () -> Unit) {
    val invoice = invoices.firstOrNull { it.key == invoiceKey } ?: invoices.first()
    var downloaded by rememberSaveable(invoiceKey) { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.primary)
            }
            Text("Factura", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(40.dp))
        }

        Card(shape = RoundedCornerShape(16.dp), border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Image(painter = painterResource(id = R.drawable.logo_aoo), contentDescription = null, modifier = Modifier.size(38.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Asociación Odontológica de Occidente", style = MaterialTheme.typography.titleMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                        Text("NIT 0614-000000-000-0 · NRC 000000-0", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Box(color = AooSuccess) { Text("DTE", style = MaterialTheme.typography.labelSmall, color = AooSuccess, fontWeight = FontWeight.ExtraBold) }
                }

                HorizontalDivider()

                Column {
                    Text("DOCUMENTO TRIBUTARIO ELECTRÓNICO", style = MaterialTheme.typography.labelSmall, color = androidx.compose.ui.graphics.Color(0xFF8A7A3A), fontWeight = FontWeight.Bold)
                    Text("Factura Electrónica", style = MaterialTheme.typography.titleLarge, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow("Número de control", invoice.folio)
                    InfoRow("Fecha de emisión", invoice.fecha)
                    InfoRow("Receptor", "Dra. Lourdes Acevedo")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Icon(Icons.Filled.QrCode2, contentDescription = null, modifier = Modifier.size(40.dp))
                    Column {
                        Text("Código de generación y sello de recepción de Hacienda", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("A1B2C3D4-0000-0000-0000-${invoice.folio}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                    }
                }

                HorizontalDivider()

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Concepto", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline, fontWeight = FontWeight.Bold)
                        Text("Total", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline, fontWeight = FontWeight.Bold)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(invoice.concepto, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Text("$${invoice.monto}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }

                HorizontalDivider()

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow("Subtotal", "$${invoice.monto}")
                    InfoRow("IVA", "Exento · Asociación sin fines de lucro")
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Total", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        Text("$${invoice.monto}", style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                    }
                }

                HorizontalDivider()

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow("Método de pago", "Tarjeta de crédito")
                    InfoRow("Pasarela", "Wompi")
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Estado", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Pagado", style = MaterialTheme.typography.bodyMedium, color = AooSuccess, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    "Esta es una representación gráfica de un Documento Tributario Electrónico. Consulte su validez en el portal del Ministerio de Hacienda.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        OutlinedButton(onClick = { downloaded = true }, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().height(50.dp)) {
            if (downloaded) {
                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                Text(" Descargado", fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Text(" Descargar PDF", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun Box(color: androidx.compose.ui.graphics.Color, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { content() }
}
