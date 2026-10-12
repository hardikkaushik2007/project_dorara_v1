package com.projectdora.v1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.PricingModel
import com.projectdora.v1.ui.theme.CyberBlue
import com.projectdora.v1.ui.theme.ElectricCyan
import com.projectdora.v1.ui.theme.NavyCard
import com.projectdora.v1.ui.theme.NavyCardBorder
import com.projectdora.v1.ui.theme.StatusAttempted
import com.projectdora.v1.ui.theme.StatusVerified
import com.projectdora.v1.ui.theme.TextMuted
import com.projectdora.v1.ui.theme.TextPrimary
import com.projectdora.v1.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppCard(
    app: AppEntity,
    isSelectedForCompare: Boolean = false,
    onCompareToggle: (() -> Unit)? = null,
    onOpenClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .border(
                width = if (isSelectedForCompare) 1.5.dp else 1.dp,
                color = if (isSelectedForCompare) ElectricCyan else NavyCardBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        // Header: Logo/Initials, Name, Category, Platform Pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Avatar / Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyberBlue.copy(alpha = 0.15f))
                    .border(1.dp, CyberBlue.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(2).uppercase(),
                    color = ElectricCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.name,
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = app.category,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }

            // Platform indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Icon(
                    imageVector = if (app.androidAvailable) Icons.Default.PhoneAndroid else Icons.Default.Public,
                    contentDescription = null,
                    tint = if (app.androidAvailable) StatusVerified else CyberBlue,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (app.androidAvailable) "Android" else "Web",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Short Description
        Text(
            text = app.description,
            color = TextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Best Use Case Highlight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(ElectricCyan.copy(alpha = 0.07f))
                .border(0.6.dp, ElectricCyan.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Column {
                Text(
                    text = "Best For",
                    color = ElectricCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = app.bestUseCase,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Suitability Explanation
        Text(
            text = "Why Dora picked this: ${app.suitabilityExplanation}",
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Pricing Tag and Limitations
        Row(verticalAlignment = Alignment.CenterVertically) {
            val pricingColor = when (app.pricingModel) {
                PricingModel.FREE -> StatusVerified
                PricingModel.FREEMIUM -> CyberBlue
                else -> StatusAttempted
            }
            Text(
                text = "${app.pricingModel.displayName}: ",
                color = pricingColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = app.pricingDetails,
                color = TextMuted,
                fontSize = 11.sp,
                maxLines = 1
            )
        }

        // Limitations Warning
        if (app.knownLimitations.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = StatusAttempted.copy(alpha = 0.8f),
                    modifier = Modifier.size(13.dp).padding(top = 1.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Limitation: ${app.knownLimitations.first()}",
                    color = StatusAttempted.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action Buttons: Open & Compare Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onOpenClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricCyan,
                    contentColor = NavyCard
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Launch,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Open App",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            if (onCompareToggle != null) {
                OutlinedButton(
                    onClick = onCompareToggle,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isSelectedForCompare) ElectricCyan else TextSecondary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (isSelectedForCompare) ElectricCyan else NavyCardBorder
                        )
                    )
                ) {
                    if (isSelectedForCompare) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = if (isSelectedForCompare) "Selected" else "Compare",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
