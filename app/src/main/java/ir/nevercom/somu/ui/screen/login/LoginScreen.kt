package ir.nevercom.somu.ui.screen.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.theme.SomuTheme
import ir.nevercom.somu.util.EmailInputState
import ir.nevercom.somu.util.PasswordInputState
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = getViewModel(), onLoggedIn: () -> Unit
) {
    val state = viewModel.viewState.observeAsState()
    val currentState = state.value!!

    if (currentState.isLoggedIn) {
        onLoggedIn()
    }
    Content(currentState = currentState, onLoginClicked = viewModel::login)


}

@Composable
private fun Content(
    currentState: LoginViewState,
    onLoginClicked: (email: String, password: String) -> Unit
) {
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
            if (currentState.errorMessage != null && currentState.errorMessage.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .height(48.dp)
                        .border(1.dp, MaterialTheme.colors.error, RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colors.error.copy(alpha = 0.25f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentState.errorMessage,
                        style = MaterialTheme.typography.overline
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(56.dp))
            }
            EmailInput(emailState)

            Spacer(Modifier.height(8.dp))

            PasswordInput(passwordState)

            Spacer(Modifier.height(48.dp))

            Button(
                enabled = !currentState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.primary.copy(
                        alpha = 0.5f
                    )
                ),
                onClick = { onLoginClicked(emailState.text, passwordState.text) },
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
fun LoginScreenPreview() {
    val currentState = LoginViewState(
        isLoading = false,
        errorMessage = "email or password is incorrect, please provide valid credentials"
    )
    SomuTheme {
        Content(
            currentState = currentState,
            onLoginClicked = { _, _ -> }
        )
    }
}