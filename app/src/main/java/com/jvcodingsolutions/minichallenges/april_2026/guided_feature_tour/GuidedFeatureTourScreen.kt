package com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSurface
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSurfaceHigher
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.Icon_ChevronLeft
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusBg
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusSurface
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusTextSecondary
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourBg
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourBody
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourButton
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourOnPrimary
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourOutline
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourPrimary
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourSubTitle
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourSurface
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourTextPrimary
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourTextSecondary
import com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme.guidedFeatureTourTitle

@Composable
fun GuidedFeatureTourScreenRoot(
    viewModel: GuidedFeatureTourViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GuidedFeatureTourScreen(
        state = state,
        onSkipTour = viewModel::onSkipTour,
        onStartTour = viewModel::onStartTour,
        onNextStep = viewModel::onNextStep,
        onFinishTour = viewModel::onFinishTour
    )
}

@Composable
private fun GuidedFeatureTourScreen(
    state: GuidedFeatureTourState,
    onSkipTour: () -> Unit,
    onStartTour: () -> Unit,
    onNextStep: () -> Unit,
    onFinishTour: () -> Unit
) {
    // Store element bounds in local coordinates (relative to this Box)
    val highlightBounds = remember { mutableStateMapOf<TutorialStep, Rect>() }
    // Track this Box's root offset so we can convert boundsInRoot -> local
    var containerTopInRoot by remember { mutableFloatStateOf(0f) }
    var containerLeftInRoot by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.guidedFeatureTourBg)
            .onGloballyPositioned { coords ->
                val pos = coords.positionInRoot()
                containerTopInRoot = pos.y
                containerLeftInRoot = pos.x
            }
    ) {
        // Main task manager UI
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.cloudPhotoBackupSurface)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Tasks",
                    style = MaterialTheme.typography.guidedFeatureTourTitle,
                    color = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary
                )

                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            val bounds = coords.boundsInRoot()
                            highlightBounds[TutorialStep.SEARCH_ICON] = bounds.translate(
                                -containerLeftInRoot, -containerTopInRoot
                            )
                        }
                        .clickable { }
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = MaterialTheme.colorScheme.guidedFeatureTourSurface,
                            shape = RoundedCornerShape(8.dp) // Apply shape here
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.guidedFeatureTourOutline,
                            shape = RoundedCornerShape(8.dp)
                        )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary
                    )
                }
            }

            // Filter tabs with underline
            val outlineColor = MaterialTheme.colorScheme.guidedFeatureTourOutline
            val primaryColor = MaterialTheme.colorScheme.guidedFeatureTourPrimary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.guidedFeatureTourSurface,
                    )
                    .onGloballyPositioned { coords ->
                        val bounds = coords.boundsInRoot()
                        highlightBounds[TutorialStep.FILTER_ROW] = bounds.translate(
                            -containerLeftInRoot, -containerTopInRoot
                        )
                    }
                    .drawBehind {
                        drawLine(
                            color = outlineColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterTab(
                    text = "All",
                    selected = true,
                    primaryColor = primaryColor,
                    modifier = Modifier.weight(1f)
                )
                FilterTab(
                    text = "Active",
                    selected = false,
                    primaryColor = primaryColor,
                    modifier = Modifier.weight(1f)
                )
                FilterTab(
                    text = "Completed",
                    selected = false,
                    primaryColor = primaryColor,
                    modifier = Modifier.weight(1f)
                )
            }

            // Task list area - empty state near top
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp)
                    .onGloballyPositioned { coords ->
                        val bounds = coords.boundsInRoot()
                        highlightBounds[TutorialStep.TASK_LIST] = bounds.translate(
                            -containerLeftInRoot, -containerTopInRoot
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No tasks yet",
                    style = MaterialTheme.typography.guidedFeatureTourSubTitle,
                    color = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap the + button to create your first task",
                    style = MaterialTheme.typography.guidedFeatureTourBody,
                    color = MaterialTheme.colorScheme.guidedFeatureTourTextSecondary
                )
            }
        }

        // FAB - add white border when step 4 is active
        val fabBorderModifier = if (state.isTutorialActive && state.currentStep == TutorialStep.FAB) {
            Modifier.border(2.dp, Color.White, RoundedCornerShape(16.dp))
        } else {
            Modifier
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .then(fabBorderModifier)
                .onGloballyPositioned { coords ->
                    val bounds = coords.boundsInRoot()
                    highlightBounds[TutorialStep.FAB] = bounds.translate(
                        -containerLeftInRoot, -containerTopInRoot
                    )
                },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.guidedFeatureTourPrimary,
            contentColor = MaterialTheme.colorScheme.guidedFeatureTourOnPrimary,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add task"
            )
        }

        // Tutorial overlay
        if (state.isTutorialActive && state.currentStep != null) {
            TutorialOverlay(
                currentStep = state.currentStep,
                highlightBounds = highlightBounds,
                onNextStep = onNextStep,
                onFinishTour = onFinishTour
            )
        }

        // Start tour dialog (bottom sheet style)
        if (state.showStartDialog) {
            StartTourDialog(
                onSkip = onSkipTour,
                onStartTour = onStartTour
            )
        }
    }
}

@Composable
private fun FilterTab(
    text: String,
    selected: Boolean,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    val textColor = if (selected) {
        MaterialTheme.colorScheme.guidedFeatureTourTextPrimary
    } else {
        MaterialTheme.colorScheme.guidedFeatureTourTextSecondary
    }

    Column(
        modifier = modifier
            .drawBehind {
                if (selected) {
                    drawLine(
                        color = primaryColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.guidedFeatureTourBody,
            color = textColor,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
private fun StartTourDialog(
    onSkip: () -> Unit,
    onStartTour: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.guidedFeatureTourSurface,
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Take a quick tour",
                    style = MaterialTheme.typography.guidedFeatureTourSubTitle,
                    color = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Learn how to manage your tasks in just a few steps",
                    style = MaterialTheme.typography.guidedFeatureTourBody,
                    color = MaterialTheme.colorScheme.guidedFeatureTourTextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary
                        )
                    ) {
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.guidedFeatureTourButton
                        )
                    }
                    Button(
                        onClick = onStartTour,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.guidedFeatureTourPrimary,
                            contentColor = MaterialTheme.colorScheme.guidedFeatureTourOnPrimary
                        )
                    ) {
                        Text(
                            text = "Start Tour",
                            style = MaterialTheme.typography.guidedFeatureTourButton
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TutorialOverlay(
    currentStep: TutorialStep,
    highlightBounds: Map<TutorialStep, Rect>,
    onNextStep: () -> Unit,
    onFinishTour: () -> Unit
) {
    val highlightRect = highlightBounds[currentStep]
    val density = LocalDensity.current
    val paddingPx = with(density) { 8.dp.toPx() }
    val cornerRadiusPx = with(density) { 12.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* consume touches */ }
    ) {
        // Semi-transparent scrim with clear cutout (no cutout for FAB step)
        val horizontalCutoutPaddingPx = with(density) { 16.dp.toPx() }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        ) {
            // Draw dimmed overlay over everything
            drawRect(color = Color.Black.copy(alpha = 0.5f))

            highlightRect?.let { rect ->
                when (currentStep) {
                    TutorialStep.FILTER_ROW, TutorialStep.TASK_LIST -> {
                        // Inset horizontally by 16dp from the element bounds
                        val highlightLeft = rect.left + horizontalCutoutPaddingPx
                        val highlightTop = rect.top - paddingPx
                        val highlightWidth = rect.width - horizontalCutoutPaddingPx * 2
                        val highlightHeight = rect.height + paddingPx * 2

                        drawRoundRect(
                            color = Color.Black,
                            topLeft = Offset(highlightLeft, highlightTop),
                            size = Size(highlightWidth, highlightHeight),
                            cornerRadius = CornerRadius(cornerRadiusPx),
                            blendMode = BlendMode.Clear
                        )
                    }
                    TutorialStep.FAB -> {
                        // Cutout exactly matching the FAB + 2dp white border
                        val borderPx = with(density) { 2.dp.toPx() }
                        val highlightLeft = rect.left - borderPx
                        val highlightTop = rect.top - borderPx
                        val highlightWidth = rect.width + borderPx * 2
                        val highlightHeight = rect.height + borderPx * 2
                        val fabCornerPx = with(density) { 16.dp.toPx() }

                        drawRoundRect(
                            color = Color.Black,
                            topLeft = Offset(highlightLeft, highlightTop),
                            size = Size(highlightWidth, highlightHeight),
                            cornerRadius = CornerRadius(fabCornerPx + borderPx),
                            blendMode = BlendMode.Clear
                        )
                    }
                    else -> {
                        // Default: expand by paddingPx
                        val highlightLeft = rect.left - paddingPx
                        val highlightTop = rect.top - paddingPx
                        val highlightWidth = rect.width + paddingPx * 2
                        val highlightHeight = rect.height + paddingPx * 2

                        drawRoundRect(
                            color = Color.Black,
                            topLeft = Offset(highlightLeft, highlightTop),
                            size = Size(highlightWidth, highlightHeight),
                            cornerRadius = CornerRadius(cornerRadiusPx),
                            blendMode = BlendMode.Clear
                        )
                    }
                }
            }
        }

        // Tooltip with arrow
        highlightRect?.let { rect ->
            TooltipWithArrow(
                step = currentStep,
                highlightRect = rect,
                onAction = if (currentStep.buttonText == "Finish") onFinishTour else onNextStep
            )
        }
    }
}

@Composable
private fun TooltipWithArrow(
    step: TutorialStep,
    highlightRect: Rect,
    onAction: () -> Unit
) {
    val density = LocalDensity.current
    val tooltipGapPx = with(density) { 12.dp.toPx() }
    val arrowSizePx = with(density) { 10.dp.toPx() }
    val arrowSizeDp = with(density) { arrowSizePx.toDp() }
    val surfaceColor = MaterialTheme.colorScheme.guidedFeatureTourSurface
    val paddingPx = with(density) { 8.dp.toPx() }

    val arrowCenterX = highlightRect.center.x

    // Horizontal padding: 46.dp for steps 2 & 3, 16.dp for step 4
    val horizontalPadding = when (step) {
        TutorialStep.FILTER_ROW, TutorialStep.TASK_LIST -> 46.dp
        else -> 16.dp
    }

    when (step) {
        TutorialStep.SEARCH_ICON -> {
            // Step 1: Tooltip to the LEFT of the search icon with arrow pointing right
            val tooltipTopPx = highlightRect.top - paddingPx
            val tooltipRightPx = highlightRect.left - paddingPx - tooltipGapPx
            val arrowCenterY = highlightRect.center.y - tooltipTopPx

            Row(
                modifier = Modifier
                    .graphicsLayer { translationY = tooltipTopPx }
                    .width(with(density) { (tooltipRightPx + arrowSizePx).toDp() })
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(surfaceColor, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TooltipBody(step = step, onAction = onAction)
                }
                // Arrow pointing right toward search icon
                Canvas(
                    modifier = Modifier
                        .width(arrowSizeDp)
                        .height(with(density) { (arrowCenterY + arrowSizePx).toDp() })
                ) {
                    val path = Path().apply {
                        moveTo(0f, arrowCenterY - arrowSizePx)
                        lineTo(arrowSizePx, arrowCenterY)
                        lineTo(0f, arrowCenterY + arrowSizePx)
                        close()
                    }
                    drawPath(path, surfaceColor)
                }
            }
        }

        TutorialStep.FAB -> {
            // Step 4: Tooltip ABOVE the FAB with arrow pointing down
            val estimatedHeightPx = with(density) { 150.dp.toPx() }
            val yOffsetPx = highlightRect.top - tooltipGapPx - arrowSizePx - estimatedHeightPx

            Column(
                modifier = Modifier
                    .graphicsLayer { translationY = yOffsetPx }
                    .padding(start = 86.dp, end = 6.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(surfaceColor, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TooltipBody(step = step, onAction = onAction)
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(arrowSizeDp)
                ) {
                    val arrowLocalX = arrowCenterX - 86.dp.toPx()
                    val path = Path().apply {
                        moveTo(arrowLocalX - arrowSizePx, 0f)
                        lineTo(arrowLocalX, arrowSizePx)
                        lineTo(arrowLocalX + arrowSizePx, 0f)
                        close()
                    }
                    drawPath(path, surfaceColor)
                }
            }
        }

        else -> {
            // Steps 2 & 3: Tooltip BELOW the highlight with arrow pointing up
            val yOffsetPx = highlightRect.bottom + paddingPx + tooltipGapPx

            Column(
                modifier = Modifier
                    .graphicsLayer { translationY = yOffsetPx }
                    .padding(horizontal = horizontalPadding)
                    .fillMaxWidth()
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(arrowSizeDp)
                ) {
                    val arrowLocalX = arrowCenterX - horizontalPadding.toPx()
                    val path = Path().apply {
                        moveTo(arrowLocalX - arrowSizePx, arrowSizePx)
                        lineTo(arrowLocalX, 0f)
                        lineTo(arrowLocalX + arrowSizePx, arrowSizePx)
                        close()
                    }
                    drawPath(path, surfaceColor)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(surfaceColor, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TooltipBody(step = step, onAction = onAction)
                }
            }
        }
    }
}

@Composable
private fun TooltipBody(
    step: TutorialStep,
    onAction: () -> Unit
) {
    Text(
        text = "Step ${step.stepNumber}/${step.totalSteps}",
        style = MaterialTheme.typography.guidedFeatureTourBody,
        color = MaterialTheme.colorScheme.guidedFeatureTourTextSecondary
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = step.description,
        style = MaterialTheme.typography.guidedFeatureTourBody,
        color = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(12.dp))

    if (step == TutorialStep.FAB) {
        Button(
            onClick = onAction,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.guidedFeatureTourPrimary,
                contentColor = MaterialTheme.colorScheme.guidedFeatureTourOnPrimary
            )
        ) {
            Text(
                text = step.buttonText,
                style = MaterialTheme.typography.guidedFeatureTourButton
            )
        }
    } else {
        OutlinedButton(
            onClick = onAction,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.guidedFeatureTourOutline
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.guidedFeatureTourTextPrimary
            )
        ) {
            Text(
                text = step.buttonText,
                style = MaterialTheme.typography.guidedFeatureTourButton
            )
        }
    }
}

@Preview
@Composable
private fun GuidedFeatureTourScreenPreview() {
    GuidedFeatureTourScreen(
        state = GuidedFeatureTourState(
            isTutorialActive = true,
            currentStep = TutorialStep.SEARCH_ICON
        ),
        onSkipTour = {},
        onStartTour = {},
        onNextStep = {},
        onFinishTour = {}
    )
}



