package com.example.gamershub.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.gamershub.R
import com.example.gamershub.domain.models.Game

import com.example.gamershub.presentation.ui.theme.DirtyWhite
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy

@Composable
fun GamesScreen(
                onGameClick: (game: Game) -> Unit,
                onSettingsClick: () -> Unit) {
    val backgroundColor = colorResource(id = R.color.dark_gray)

    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val gamesByGenreState = gamesViewModel.gamePager.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        GamesColumnWithGrids(listOfGames = gamesByGenreState, onGameClick = { game ->
            onGameClick(game)
        }, onSettingsClick)
    }

    //    else {
    //        Box(
    //            Modifier
    //                .fillMaxSize()
    //                .background(backgroundColor),
    //            contentAlignment = Center
    //        ) {
    //            if (games.isLoading) {
    //                CircularProgressIndicator()
    //            } else if (games.error != null) {
    //                Text(
    //                    text = games.error,
    //                    color = Color.Red,
    //                    textAlign = TextAlign.Center,
    //                    modifier = Modifier.align(Alignment.Center)
    //                )
    //            }
    //        }
    //    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp), strokeWidth = 5.dp
        )
    }
}

@Composable
fun GamesColumnWithGrids(
    listOfGames: LazyPagingItems<Game>,
    onGameClick: (game: Game) -> Unit,
    onSettingsClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleAndSettings(onSettingsClick)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            items(listOfGames.itemCount) { item ->
                listOfGames[item]?.let {
                    GamesDetailsItem(
                        { onGameClick(it) },
                        game = it
                    )
                }
            }
            when (listOfGames.loadState.append) {
                is LoadState.Error -> {
                    item {

                    }
                }

                LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }
    }
}

@Composable
private fun TitleAndSettings(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🎮 Click on the game to see more details!",
            color = DirtyWhite,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Icon(
            Icons.Rounded.Settings,
            contentDescription = "Settings",
            tint = Color.LightGray,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onSettingsClick() }
        )
    }
}

@Composable
fun GamesDetailsItem(
    onGameClick: () -> Unit,
    game: Game
) {

    val backgroundColor = colorResource(id = R.color.gray)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onGameClick() },
        contentAlignment = Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(6.dp)
        ) {

            AsyncImage(
                model = game.backgroundImage,
                contentDescription = "Genre Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)), contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                game.name,
                fontSize = 12.sp,
                color = DirtyWhite,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            var rating by remember { mutableStateOf(game.rating.toFloat()) }

            val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
            val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)

            RatingBar(
                rating = rating,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                itemSize = 10.dp,
                gestureStrategy = GestureStrategy.None,

                ) {
                rating = it
            }
        }

    }

}