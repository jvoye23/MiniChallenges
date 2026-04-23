package com.jvcodingsolutions.minichallenges.april_2026.ready_to_type

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.ReadyToTypeTheme
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeBg
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeBody
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeInput
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeInputActive
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeInputInactive
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTextDisabled
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTextError
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTextPrimary
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTextSecondary
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTextSuccess
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme.readyToTypeTitle
import kotlinx.coroutines.delay

/**
 * Represents the 3 states of our PIN indicator.
 */
enum class PinState {
    INACTIVE, SUCCESS, ERROR
}

/**
 * Stateful entry point handling the business logic, focus requests, and side-effects.
 */
@Composable
fun ReadyToTypeScreenRoot() {
    var pinValues by remember { mutableStateOf(List(4) { "" }) }
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    val pinText = pinValues.joinToString("")
    val pinState = when {
        pinText.length < 4 -> PinState.INACTIVE
        pinText == "2580" -> PinState.SUCCESS
        else -> PinState.ERROR
    }

    // Side-Effect: Initial Focus & Keyboard
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
        keyboardController?.show()
    }

    // Side-Effect: Error State Handling
    LaunchedEffect(pinState) {
        if (pinState == PinState.ERROR) {
            delay(700)
            pinValues = List(4) { "" }
            focusRequesters[0].requestFocus()
        }
    }

    ReadyToTypeScreen(
        pinValues = pinValues,
        pinState = pinState,
        focusRequesters = focusRequesters,
        onPinDigitChanged = { index, newValue ->
            // Prevent input if we are in a terminal state (delaying error reset or already success)
            if (pinState == PinState.SUCCESS || pinState == PinState.ERROR) return@ReadyToTypeScreen

            val digitsOnly = newValue.filter { it.isDigit() }

            if (digitsOnly.isNotEmpty()) {
                // Keep only the last typed character in case of rapid typing/overwriting
                val digit = digitsOnly.last().toString()
                val newPin = pinValues.toMutableList()
                newPin[index] = digit
                pinValues = newPin

                // Auto-advance to the next field if available
                if (index < 3) {
                    focusRequesters[index + 1].requestFocus()
                }
            } else {
                // Field was explicitly cleared by backspace (had a digit, now empty)
                val newPin = pinValues.toMutableList()
                newPin[index] = ""
                pinValues = newPin
            }
        },
        onBackspaceOnEmpty = { index ->
            // Move focus backward when deleting on an already empty field
            if (index > 0) {
                focusRequesters[index - 1].requestFocus()
            }
        }
    )
}

/**
 * Stateless UI component for displaying the Vault PIN UI.
 */
@Composable
fun ReadyToTypeScreen(
    pinValues: List<String>,
    pinState: PinState,
    focusRequesters: List<FocusRequester>,
    onPinDigitChanged: (index: Int, value: String) -> Unit,
    onBackspaceOnEmpty: (index: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        color = MaterialTheme.colorScheme.readyToTypeBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Secure Vault",
                style = MaterialTheme.typography.readyToTypeTitle,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.readyToTypeTextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Unlock your vault using your 4-digit PIN",
                style = MaterialTheme.typography.readyToTypeBody,
                color = MaterialTheme.colorScheme.readyToTypeTextSecondary
            )

            Spacer(modifier = Modifier.height(48.dp))

            // PIN Input Row
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                for (i in 0 until 4) {
                    PinDigitField(
                        value = pinValues[i],
                        onValueChange = { onPinDigitChanged(i, it) },
                        onBackspaceOnEmpty = { onBackspaceOnEmpty(i) },
                        focusRequester = focusRequesters[i]
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Confirmation Indicator
            val indicatorText = when (pinState) {
                PinState.INACTIVE -> "Enter PIN"
                PinState.SUCCESS -> "Unlocked successfully"
                PinState.ERROR -> "Wrong PIN"
            }

            val indicatorColor = when (pinState) {
                PinState.INACTIVE -> MaterialTheme.colorScheme.readyToTypeTextDisabled
                PinState.SUCCESS -> MaterialTheme.colorScheme.readyToTypeTextSuccess
                PinState.ERROR -> MaterialTheme.colorScheme.readyToTypeTextError
            }

            Text(
                text = indicatorText,
                style = MaterialTheme.typography.readyToTypeBody,
                fontWeight = FontWeight.Medium,
                color = indicatorColor
            )
        }
    }
}

/**
 * Represents a single digit input box.
 */
@Composable
fun PinDigitField(
    value: String,
    onValueChange: (String) -> Unit,
    onBackspaceOnEmpty: () -> Unit,
    focusRequester: FocusRequester
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .size(56.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .onKeyEvent { event ->
                // Intercept hardware/software Backspace keystrokes when the field is already empty
                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace) {
                    if (value.isEmpty()) {
                        onBackspaceOnEmpty()
                        return@onKeyEvent true
                    }
                }
                false
            }
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) MaterialTheme.colorScheme.readyToTypeInputActive else MaterialTheme.colorScheme.readyToTypeInputInactive,
                shape = RoundedCornerShape(12.dp)
            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        textStyle = MaterialTheme.typography.readyToTypeInput.copy(
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.readyToTypeTextPrimary
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()

            ) {
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
private fun ReadyToTypeScreenPreview() {
    ReadyToTypeTheme {
        ReadyToTypeScreen(
            pinValues = listOf("2", "5", "8", "0"),
            pinState = PinState.SUCCESS,
            focusRequesters = remember { listOf(FocusRequester(), FocusRequester(), FocusRequester(), FocusRequester()) },
            onPinDigitChanged = { _, _ -> },
            onBackspaceOnEmpty = { _ -> }
        )
    }
}