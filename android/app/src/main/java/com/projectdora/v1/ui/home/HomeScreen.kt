package com.projectdora.v1.ui.home

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectdora.v1.ui.components.DoraOrb
import com.projectdora.v1.ui.components.StatusPill
import com.projectdora.v1.ui.components.VoiceBar
import com.projectdora.v1.ui.theme.CyberBlue
import com.projectdora.v1.ui.theme.ElectricCyan
import com.projectdora.v1.ui.theme.NavyBackground
import com.projectdora.v1.ui.theme.NavyCard
import com.projectdora.v1.ui.theme.NavyCardBorder
import com.projectdora.v1.ui.theme.TextMuted
import com.projectdora.v1.ui.theme.TextPrimary
import com.projectdora.v1.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDiscover: (String) -> Unit,
    onNavigateToWorkspace: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadRecents()
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is HomeNavigationEvent.NavigateToDiscover -> onNavigateToDiscover(event.query)
                is HomeNavigationEvent.NavigateToWorkspace -> onNavigateToWorkspace(event.appId, event.query)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NavyBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Futuristic Animated Dora Orb Avatar
        DoraOrb(
            state = uiState.orbState,
            size = 170.dp,
            onClick = { viewModel.onMicClicked() }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Natural Greeting
        Text(
            text = uiState.greeting,
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Bolo ya type karo jo accomplish karna hai. Dora best tools suggest karegi.",
            color = TextSecondary,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Voice & Text Input Bar
        VoiceBar(
            isListening = uiState.isListening,
            liveTranscript = uiState.liveTranscript,
            onMicClick = { viewModel.onMicClicked() },
            onCancelListening = { viewModel.onCancelListening() },
            onSubmitText = { viewModel.submitTextQuery(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Example Request Chips Section
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "TRY ASKING DORA",
                color = ElectricCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.suggestedChips.forEach { chipText ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(NavyCard)
                            .border(0.8.dp, NavyCardBorder, RoundedCornerShape(20.dp))
                            .clickable { viewModel.submitTextQuery(chipText) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = chipText,
                            color = TextPrimary,
                            fontSize = 12.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Recent Applications Section
        if (uiState.recentApps.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "RECENT & FEATURED TOOLS",
                    color = CyberBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    uiState.recentApps.forEach { app ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NavyCard)
                                .border(0.8.dp, NavyCardBorder, RoundedCornerShape(12.dp))
                                .clickable {
                                    onNavigateToWorkspace(app.id, "Research with ${app.name}")
                                }
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = app.name.take(2).uppercase(),
                                    color = ElectricCyan,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = app.name,
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Recent Task Summaries
        if (uiState.recentTasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "RECENT TASK SESSIONS",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                uiState.recentTasks.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(NavyCard)
                            .border(0.8.dp, NavyCardBorder, RoundedCornerShape(10.dp))
                            .clickable {
                                onNavigateToWorkspace(
                                    task.selectedAppId ?: "perplexity",
                                    task.originalRequest
                                )
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = CyberBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.originalRequest,
                                color = TextPrimary,
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "App: ${task.selectedAppName ?: "Selected"}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                        StatusPill(status = task.status)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
