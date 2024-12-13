package com.example.healthyeats

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthyeats.ui.theme.HealthyEatsTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(applicationContext)
        setContent {
            HealthyEatsTheme {
                EmailLoginOrRegisterScreen()
            }
        }
    }
}

@Composable
fun EmailLoginOrRegisterScreen() {
    var isLogin by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLogin) {
            EmailLoginForm(onSwitchToRegister = { isLogin = false })
        } else {
            EmailRegisterForm(onSwitchToLogin = { isLogin = true })
        }
    }
}

@Composable
fun EmailLoginForm(onSwitchToRegister: () -> Unit) {
    var isEmailValid by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("2@q.com") }
    var password by remember { mutableStateOf("111111") }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //title
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(
                color = Color(0xFF70B53F),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        //input email
        TextField(value = email, onValueChange = {
            email = it
            errorMessage = null
        },
            label = { Text(stringResource(R.string.email)) },
            supportingText = {
                if (!isEmailValid && email.isNotEmpty()) {
                    Text(text = stringResource(R.string.email_is_invalid))
                }
            })
        //input password
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    delay(2000L)
                    isLoading = false
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                }
            }, modifier = Modifier
                .width(196.dp)
        ) {
            Text(text = stringResource(R.string.login))
        }
        TextButton(onClick = onSwitchToRegister) {
            Text(stringResource(R.string.no_account_register))
        }
    }
}

fun login(email: String, password: String) {

}

fun signUp(email: String, password: String) {

}

@Composable
fun EmailRegisterForm(onSwitchToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordMatch by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    //check email
    LaunchedEffect(email) {
        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //check password
    LaunchedEffect(password, confirmPassword) {
        isPasswordMatch = password == confirmPassword
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(
                color = Color(0xFF70B53F),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        //input email
        TextField(value = email, onValueChange = {
            email = it
            errorMessage = null
        },
            label = { Text(text = stringResource(R.string.email)) },
            isError = !isEmailValid && email.isNotEmpty(),
            supportingText = {
                if (!isEmailValid && email.isNotEmpty()) {
                    Text(text = stringResource(R.string.email_is_invalid))
                }
            })
        //input password
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            isError = password.isNotEmpty() && !isPasswordMatch,
            supportingText = {
                if (password.isNotEmpty() && !isPasswordMatch) {
                    Text(text = stringResource(R.string.password_mismatch_in_two_type))
                }
            }
        )
        //confirm password
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.confirm_password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        //sign up button
        Button(
            onClick = {
                if (isEmailValid && isPasswordMatch && password.length >= 6) {
                    signUp(email, password)
                } else {
                    if (!isEmailValid) {
                        errorMessage = "Please enter a valid email address"
                    } else if (!isPasswordMatch) {
                        errorMessage = "Passwords do not match"
                    } else if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters long"
                    }
                }
            },
            modifier = Modifier.width(196.dp),
        ) {
            Text(text = stringResource(R.string.sign_up))
        }
        TextButton(onClick = onSwitchToLogin) {
            Text(stringResource(R.string.have_an_account_sign_in))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailLoginOrRegisterScreen() {
    HealthyEatsTheme {
        EmailLoginOrRegisterScreen()
    }
}