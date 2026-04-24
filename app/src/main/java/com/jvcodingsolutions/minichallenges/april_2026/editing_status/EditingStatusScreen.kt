package com.jvcodingsolutions.minichallenges.april_2026.editing_status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.EditingStatusBg
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.Icon_ChevronLeft
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusBg
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusSurface
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusTextPrimary
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme.editingStatusTextSecondary


/**
 * Main Composable implementing the UI requirements.
 * Note: To ensure `imePadding()` works perfectly, ensure your Activity calls
 * `WindowCompat.setDecorFitsSystemWindows(window, false)`
 */
@Composable
fun EditingStatusScreenRoot(viewModel: EditingStatusViewModel = viewModel()) {
    val text by viewModel.text.collectAsStateWithLifecycle()
    val status by viewModel.status.collectAsStateWithLifecycle()

    EditingStatusScreen(
        editingStatus = status,
        textState = text,
        onValueChange = viewModel::onTextChanged
    )
}

@Composable
private fun EditingStatusScreen(
    editingStatus: EditingState,
    onValueChange: (String) -> Unit,
    textState: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.editingStatusBg)
            .padding(16.dp)

    ) {

        Box(
            modifier = Modifier
                .clickable { }
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = MaterialTheme.colorScheme.editingStatusBg,
                    shape = RoundedCornerShape(8.dp) // Apply shape here
                )

                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.editingStatusSurface,
                    shape = RoundedCornerShape(8.dp)
                )
                ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                tint = MaterialTheme.colorScheme.editingStatusTextSecondary,
                contentDescription = null,
                imageVector = Icon_ChevronLeft
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Title
        Text(
            text = "My Note",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.editingStatusTextPrimary
        )

        // 2. Multiline Text Input Field
        TextField(
            value = textState,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            placeholder = { Text("Enter Note") },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.editingStatusSurface,
                unfocusedContainerColor = MaterialTheme.colorScheme.editingStatusSurface,
                cursorColor = MaterialTheme.colorScheme.editingStatusTextPrimary,
                focusedTextColor = MaterialTheme.colorScheme.editingStatusTextPrimary
            )
        )

        // 3. Status Indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (editingStatus != EditingState.Hidden) {
                Text(
                    text = when (editingStatus) {
                        EditingState.Editing -> "Editing..."
                        EditingState.Saved -> "Saved"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
private fun EditingStatusScreenPreview() {
    EditingStatusScreen(
        editingStatus = EditingState.Editing,
        onValueChange = {},
        textState = "Editing"
    )
}
