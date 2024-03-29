package com.example.gamershub.presentation.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamershub.R
import com.example.gamershub.data.preferences.DeviceSharedPreferences
import com.example.gamershub.domain.models.GenreDetails
import com.example.gamershub.presentation.ui.theme.DirtyWhite

@Composable
fun GenreScreen(
    state: GenreState,
    onGenresClick: (genres: List<GenreDetails>) -> Unit,
    sharedPreferences: DeviceSharedPreferences
) {
    val backgroundColor = colorResource(id = R.color.dark_gray)
    if (state.genre != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            GenresColumnWithGrids(data = state.genre.results, onItemSelected = { selectedItems ->
                val genreIds = selectedItems.map { it.id }.joinToString(",")
                sharedPreferences.setGenreIds(genreIds)
                onGenresClick(selectedItems)
            })
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GenresColumnWithGrids(
    data: List<GenreDetails>,
    onItemSelected: (List<GenreDetails>) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<GenreDetails>() }
    val buttonColor = colorResource(id = R.color.dark_gray)
    val textColor = colorResource(id = R.color.gray)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "🎮 Please pick one or more game genres!",
            color = DirtyWhite,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp, top = 16.dp)
                .fillMaxWidth()
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {

            items(data) { item ->
                GenreDetailsItem(
                    genreDetails = item,
                    onItemClick = { selectedGenre ->
                        if (selectedItems.contains(selectedGenre)) {
                            selectedItems.remove(selectedGenre)
                        } else {
                            selectedItems.add(selectedGenre)
                        }
                    },
                    isSelected = selectedItems.contains(item)
                )
            }
        }
        Button(
            onClick = {
                onItemSelected(selectedItems)
            },
            colors = ButtonDefaults.buttonColors(if (selectedItems.isNotEmpty()) Color.White else buttonColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            enabled = selectedItems.isNotEmpty()
        ) {
            Text(
                "Show me the games!",
                color = if (selectedItems.isNotEmpty()) textColor else Color.Black
            )
        }
    }
}

@Composable
fun GenreDetailsItem(
    genreDetails: GenreDetails,
    onItemClick: (GenreDetails) -> Unit,
    isSelected: Boolean
) {
    val textBoxSize = 200.dp
    val backgroundColor = colorResource(id = R.color.gray)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(textBoxSize)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Color.White else backgroundColor)
            .clickable { onItemClick(genreDetails) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(6.dp)
        ) {

            AsyncImage(
                model = genreDetails.imageBackground,
                contentDescription = "Genre Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)), contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                genreDetails.name,
                color = if (isSelected) backgroundColor else Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

        }

    }

}





