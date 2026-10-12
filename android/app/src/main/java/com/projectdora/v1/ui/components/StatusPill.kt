package com.projectdora.v1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdora.v1.core.model.ActionStatus
import com.projectdora.v1.ui.theme.StatusAttempted
import com.projectdora.v1.ui.theme.StatusFailed
import com.projectdora.v1.ui.theme.StatusRequested
import com.projectdora.v1.ui.theme.StatusVerified

@Composable
fun StatusPill(
    status: ActionStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, dotColor) = when (status) {
        ActionStatus.VERIFIED -> Triple(StatusVerified.copy(alpha = 0.15f), StatusVerified, StatusVerified)
        ActionStatus.COMPLETED -> Triple(StatusVerified.copy(alpha = 0.12f), StatusVerified, StatusVerified)
        ActionStatus.ATTEMPTED -> Triple(StatusAttempted.copy(alpha = 0.15f), StatusAttempted, StatusAttempted)
        ActionStatus.REQUESTED -> Triple(StatusRequested.copy(alpha = 0.15f), StatusRequested, StatusRequested)
        ActionStatus.FAILED -> Triple(StatusFailed.copy(alpha = 0.15f), StatusFailed, StatusFailed)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(0.8.dp, textColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = status.label,
            color = textColor,
            fontSize = 11.sp,
            lineHeight = 13.sp
        )
    }
}
