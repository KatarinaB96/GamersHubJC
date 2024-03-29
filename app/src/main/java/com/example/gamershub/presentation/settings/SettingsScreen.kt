package com.example.gamershub.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamershub.R
import com.example.gamershub.presentation.sign_in.UserData
import com.example.gamershub.presentation.ui.theme.DirtyWhite

@Composable
fun SettingsScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    onReturnHome: () -> Unit,
    onChangeGenres: () -> Unit

) {
    val backgroundColor = colorResource(id = R.color.dark_gray)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.userName != null) {
            Text(
                text = userData.userName,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.userId != null) {
            Button(
                onClick = onSignOut,
                colors = ButtonDefaults.buttonColors(DirtyWhite)
            ) {
                Text(text = "Sign out", color = Color.DarkGray)
            }
        }

        Button(
            onClick = onReturnHome,
            colors = ButtonDefaults.buttonColors(DirtyWhite)
        ) {
            Text(text = "Return to the main screen", color = Color.DarkGray)
        }
        Button(onClick = onChangeGenres, colors = ButtonDefaults.buttonColors(DirtyWhite)) {
            Text(text = "Change Genres", color = Color.DarkGray)
        }

    }
}






