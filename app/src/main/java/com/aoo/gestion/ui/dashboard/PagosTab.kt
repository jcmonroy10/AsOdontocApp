package com.aoo.gestion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import java.util.Locale

private data class PendingItem(val id: Int, val label: String, val dueLabel: String, val amount: Double, var selected: Boolean)

private data class HistoryItem(val invoiceKey: String, val label: String, val dateLabel: String, val folio: String, val amount: Double, val icon: androidx.compose.ui.graphics.vector.ImageVector, val iconColor: androidx.compose.ui.graphics.Color)

private val historyItems = listOf(
    HistoryItem("sep", "Cuota de septiembre", "05 sep 2026", "DTE-0429", 15.0, Icons.AutoMirrored.Filled.ReceiptLong, AooSuccess),
    HistoryItem("congreso25", "Inscripción · Congreso Gremial 2025", "03 oct 2025", "DTE-0311", 10.0, Icons.Filled.Event, androidx.compose.ui.graphics.Color(0xFF32306B)),
    HistoryItem("ago", "Cuota de agosto", "04 ago 2026", "DTE-0402", 15.0, Icons.AutoMirrored.Filled.ReceiptLong, AooSuccess),
    HistoryItem("jul", "Cuota de julio", "03 jul 2026", "DTE-0378", 15.0, Icons.AutoMirrored.Filled.ReceiptLong, AooSuccess)
)

@Composable
fun PagosTab(onOpenInvoice: (String) -> Unit, onPaySelected: (String, String) -> Unit) {
    var pending by remember {
        mutableStateOf(
            listOf(
                PendingItem(1, "Cuota de octubre", "Vence 05 nov 2026", 15.0, true),
                PendingItem(2, "Inscripción · Congreso Gremial 2026", "Vence 20 oct 2026", 25.0, false)
            )
        )
    }

    val total = pending.filter { it.selected }.sumOf { it.amount }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (pending.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Pendientes de pago", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                pending.forEachIndexed { index, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                pending = pending.toMutableList().also { it[index] = item.copy(selected = !item.selected) }
                            }
                            .padding(vertical = 10.dp)
                    ) {
                        if (item.selected) {
                            Box(
                                modifier = Modifier.size(20.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(6.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(12.dp))
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(androidx.compose.ui.graphics.Color.Transparent, RoundedCornerShape(6.dp))
                                    .then(Modifier.border(1.5.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(6.dp)))
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text(item.dueLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(formatUsd(item.amount), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(top = 6.dp)) {
                    Text("Total seleccionado", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(formatUsd(total), style = MaterialTheme.typography.headlineMedium, fontFamily = SpectralFamily, fontWeight = FontWeight.SemiBold)
                }
                Button(
                    onClick = {
                        val concepto = pending.filter { it.selected }.joinToString(" + ") { it.label }
                        onPaySelected(concepto.ifBlank { "Cuota de octubre" }, formatUsd(total).removePrefix("$"))
                    },
                    enabled = total > 0,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp)
                ) {
                    Text("Pagar seleccionados", fontWeight = FontWeight.Bold)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                Box(modifier = Modifier.size(30.dp).background(AooSuccess.copy(alpha = 0.12f), RoundedCornerShape(50)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = AooSuccess, modifier = Modifier.size(15.dp))
                }
                Text("Estás al día con tus pagos", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
        }

        Text("Historial", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            historyItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenInvoice(item.invoiceKey) }
                        .padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(38.dp).background(item.iconColor.copy(alpha = 0.1f), RoundedCornerShape(11.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(item.icon, contentDescription = null, tint = item.iconColor, modifier = Modifier.size(17.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Text("${item.dateLabel} · ${item.folio}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(formatUsd(item.amount), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        Text("Pagado", style = MaterialTheme.typography.labelSmall, color = AooSuccess, fontWeight = FontWeight.Bold)
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                }
                HorizontalDivider()
            }
        }
    }
}

private fun formatUsd(amount: Double): String = "$" + String.format(Locale.US, "%.2f", amount)
