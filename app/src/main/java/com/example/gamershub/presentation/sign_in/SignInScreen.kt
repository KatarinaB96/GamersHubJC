package com.example.gamershub.presentation.sign_in

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gamershub.R
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInWithGoogleClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    val backgroundColor = colorResource(id = R.color.dark_gray)
    val lineColor = colorResource(id = R.color.dirty_white)
    val buttonColor = colorResource(id = R.color.gray)

    val context = LocalContext.current
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        state.singInErrorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(6.dp)
    ) {
        val (pager, button1, text1, button2) = createRefs()
        PagerComponent(
            Modifier
                .constrainAs(pager) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button1.top)
                })


        Button(onClick = { onSignInWithGoogleClick() },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier
                .padding(top = 34.dp)
                .constrainAs(button1) {
                    bottom.linkTo(text1.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }) {

            Text(text = "Sign In with Google", color = lineColor)
        }

        Text(text = "OR",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = lineColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(text1) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button2.top, margin = 16.dp)
                })

        Text(
            text = "Sign In as guest",
            fontSize = 18.sp,
            color = lineColor,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .clickable { onSignInClick() }
                .constrainAs(button2) {
                    bottom.linkTo(parent.bottom, margin = 36.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
        )


    }
}

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerComponent(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.banishers_hhosts_of_new_eden,
        R.drawable.the_day_before,
        R.drawable.valhalla,
    )
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { images.size })
    val textColor = colorResource(id = R.color.light_gray)
    Box(
        modifier
            .height(500.dp)
            .padding(top = 26.dp)

    ) {
        HorizontalPager(
            state = pagerState, key = { images[it] }, pageSize = PageSize.Fill
        ) { index ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(5)),
            ) {

                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                when (index) {
                    0 -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp), contentAlignment = Alignment.CenterStart
                    ) {
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            ) {
                                append("Explore ")
                            }
                            pushStringAnnotation(tag = "rawg", annotation = "https://rawg.io/")
                            withStyle(
                                style = SpanStyle(
                                    color = textColor, textDecoration = TextDecoration.Underline, fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("RAWG")
                            }
                            pop()
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            ) {
                                append(" Video Games\nDatabase API")
                            }
                        }
                        ClickableText(text = annotatedString, style = MaterialTheme.typography.body1, onClick = { offset ->
                            annotatedString.getStringAnnotations(tag = "rawg", start = offset, end = offset).firstOrNull()?.let {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.item))
                                ContextCompat.startActivity(context, intent, null)
                            }

                            annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset).firstOrNull()?.let {

                            }
                        })

                    }

                    1 -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp, bottom = 26.dp), contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = "RAWG is the largest video game database\nand game discovery service",
                            style = TextStyle(color = Color.White, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
                        )
                    }

                    else -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 26.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "We are gladly sharing our 500,000+ games, search, and machine learning recommendations with the world.",
                            style = TextStyle(color = Color.White, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic),
                            modifier = Modifier.offset(y = (-20).dp), textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }


        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp),
            pageCount = images.size,
            pagerState = pagerState,
        )

    }

}