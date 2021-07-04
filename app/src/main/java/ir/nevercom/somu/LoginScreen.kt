package ir.nevercom.somu

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.nevercom.somu.ui.theme.SomuTheme

@Composable
fun LoginScreen() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            val emailState = remember {
                EmailInputState()
            }
            val passwordState = remember {
                PasswordInputState()
            }
            Logo()

            EmailInput(emailState)

            Spacer(Modifier.height(8.dp))

            PasswordInput(passwordState)

            Spacer(Modifier.height(48.dp))

            Button(
                onClick = {
                    Log.d("LoginScreen", "Email: ${emailState.text}, Password: ${passwordState.text}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 48.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
fun PasswordInput(state: PasswordInputState) {
    OutlinedTextField(
        value = state.text,
        onValueChange = { newString ->
            state.text = newString
        },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = if (state.shouldHidePassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val isFocused = focusState.isFocused
                state.onFocusChange(isFocused)

                if (!isFocused) {
                    state.enableShowErrors()
                }
            },
        isError = state.showErrors,
        trailingIcon = {
            Crossfade(targetState = state.shouldHidePassword) { hidePassword ->
                if (hidePassword) {
                    PasswordVisibilityIcon(
                        iconToUse = Icons.Default.VisibilityOff,
                        textState = state
                    )
                } else {
                    PasswordVisibilityIcon(
                        iconToUse = Icons.Default.Visibility,
                        textState = state
                    )
                }
            }
        }
    )
    state.getError()?.let { errorMessage ->
        TextFieldError(textError = errorMessage)
    }
}

@Composable
private fun TextFieldError(textError: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}

@Composable
private fun PasswordVisibilityIcon(
    iconToUse: ImageVector,
    textState: PasswordInputState
) {
    Icon(
        iconToUse,
        contentDescription = "Toggle Password Visibility",
        modifier = Modifier
            .clickable {
                textState.shouldHidePassword = !textState.shouldHidePassword
            },
    )
}

@Composable
fun EmailInput(state: EmailInputState) {
    OutlinedTextField(
        value = state.text,
        onValueChange = { newString ->
            state.text = newString
        },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Logo() {
    Image(
        painterResource(id = R.drawable.logo),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .padding(top = 64.dp, bottom = 48.dp)
            .size(128.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    SomuTheme {
        LoginScreen()
    }
}