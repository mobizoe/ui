package com.mobizoe.ui

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onSignInClick: (String, String) -> Unit,
    googleSignIn: () -> Unit,
    onSignUpClick: () -> Unit,
    allPadding: PaddingValues,
    loginBackground: Color,
    headIcon: Int?,
    iconTintColor: Color,
    titleText: String,
    subTitleText: String,
    emailLabel: String,
    emailErrorText: String,
    passwordLabel: String,
    passwordErrorText: String,
    signInText: String,
    doYouHaveAccountText: String,
    signUpText: String,
    signInWithGoogleText: String,
    signInWithGoogleTextColor: Color,
    signUpTextColor: Color,
    googleLogo: Int,
    visiblePasswordIcon: ImageVector,
    invisiblePasswordIcon: ImageVector,
    buttonContainerColor: Color,
    googleSignInContainerColor: Color,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val isFormValid = !emailError && !passwordError

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(loginBackground)
            .padding(allPadding),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Logo
                if (headIcon != null) {
                    Icon(
                        painter = painterResource(id = headIcon),
                        contentDescription = "Head Icon",
                        tint = iconTintColor,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(titleText, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = subTitleText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    label = { Text(text = emailLabel) },
                    isError = emailError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError) {
                    Text(
                        text = emailErrorText,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6
                    },
                    label = { Text(passwordLabel) },
                    isError = passwordError,
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) visiblePasswordIcon else invisiblePasswordIcon,
                                contentDescription = "Toggle Password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (passwordError) {
                    Text(
                        text = passwordErrorText,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sign In Button
                Button(
                    onClick = { onSignInClick(email, password) },
                    enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonContainerColor)
                ) {
                    Text(signInText)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign Up Prompt
                Row {
                    Text(doYouHaveAccountText)
                    Text(
                        signUpText,
                        color = signUpTextColor,
                        modifier = Modifier.clickable { onSignUpClick() }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box {
                    Button(
                        onClick = { googleSignIn() },
                        enabled = isFormValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = googleSignInContainerColor)
                    ) {
                        Text(text = signInWithGoogleText, color = signInWithGoogleTextColor)
                    }
                    Icon(
                        painter = painterResource(id = googleLogo),
                        contentDescription = "Google Login",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }

            }
        }
    }
}
