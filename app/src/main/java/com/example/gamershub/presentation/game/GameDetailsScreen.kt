package com.example.gamershub.presentation.game

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.example.gamershub.R
import com.example.gamershub.domain.models.Game
import com.example.gamershub.presentation.ui.theme.DirtyWhite

@Composable
fun GameDetailsScreen(state: GameState) {
    val backgroundColor = colorResource(id = R.color.dark_gray)

    if (state.game != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameDetails(game = state.game)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor), contentAlignment = Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun GameDetails(game: Game) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, bottom = 36.dp, top = 36.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = game.name, color = DirtyWhite, fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = game.backgroundImage, contentDescription = "gameImage", modifier = Modifier
                .height(150.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = removeHtmlTags(htmlString = game.description), color = DirtyWhite
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Game was released ${game.released}", color = DirtyWhite
        )
        Spacer(modifier = Modifier.height(10.dp))
        WebsiteClickableText(game.website)

    }
}

@Composable
fun removeHtmlTags(htmlString: String): AnnotatedString {

    val cleanText = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT)

    return buildAnnotatedString {
        withStyle(SpanStyle()) {
            append(cleanText)
        }
    }
}

@Composable
fun WebsiteClickableText(website: String) {
    val context = LocalContext.current
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = DirtyWhite)
        ) {
            append("Visit the site ")
        }
        pushStringAnnotation(
            tag = "URL",
            annotation = website
        )
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = DirtyWhite)) {
            append(website)
        }
        pop()
    }

    ClickableText(
        text = text,
        onClick = { offset ->
            text.getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let { annotation ->
                    val url = annotation.item
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, intent, null)
                }
        },
        modifier = Modifier
    )
}