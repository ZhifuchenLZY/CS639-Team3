package com.example.healthyeats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthyeats.ui.theme.HealthyEatsTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        TextField(value = email, onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                /* Handle login */
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

@Composable
fun EmailRegisterForm(onSwitchToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
        TextField(value = email, onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.email)) })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.confirm_password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = { /* Handle register */ },
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