package com.example.travenorowner.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travenorowner.R
import com.example.travenorowner.features.Components.TravenorAsyncImage
import com.example.travenorowner.features.Components.TravenorShimmerLoadingOverlay
import com.example.travenorowner.model.Destination
import com.example.travenorowner.ui.theme.AppColors
import com.example.travenorowner.ui.theme.AppTypography
import org.koin.androidx.compose.koinViewModel
import kotlin.toString

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state = viewModel.stateFlow.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SearchEvent.NavigateToDetailScreen -> {}
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Search",
                    style = AppTypography.titleMediumSfUi,
                    modifier = Modifier.align(Alignment.Center)
                )

                Text(
                    text = "Cancel",
                    color = AppColors.primaryBlue,
                    style = AppTypography.labelLargeSemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            SearchBar(
                value = state.searchQuery,
                onValueChange = { viewModel.onAction(SearchAction.OnSearchQueryChange(it)) }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Search Places",
                style = AppTypography.titleLarge,
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (state.filterDestination.isNotEmpty()) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    items(state.filterDestination) { place ->
                        PlaceCard(place)
                    }
                }
            } else {
                Box(
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No Destination Found",
                        style = AppTypography.labelLargeRegular.copy(color = AppColors.grey)
                    )
                }
            }
        }

        if (state.isLoading) {
            TravenorShimmerLoadingOverlay()
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = AppColors.grey.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            tint = AppColors.grey,
            modifier = Modifier.padding(start = 20.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    "Search Places",
                    style = AppTypography.labelLargeRegular.copy(color = AppColors.grey)
                )
            },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = AppTypography.labelLargeRegular.copy(color = AppColors.grey)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            VerticalDivider(
                modifier = Modifier
                    .width(1.5.dp)
                    .height(25.dp)
                    .background(
                        color = AppColors.grey.copy(alpha = 0.3f)
                    )
            )

            Icon(
                painter = painterResource(R.drawable.mic),
                contentDescription = null,
                tint = AppColors.grey,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}

@Composable
fun PlaceCard(destination: Destination) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {

                }
            )
            .size(height = 216.dp, width = 161.dp),
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            TravenorAsyncImage(
                image = destination.coverImage,
                modifier = Modifier
                    .size(width = 137.dp, height = 124.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Text(
                text = destination.destinationName,
                style = AppTypography.titleSmall,
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.location),
                    contentDescription = null,
                    tint = AppColors.lightSub,
                    modifier = Modifier.size(11.dp)
                )

                Text(
                    text = "${destination.city}, ${destination.country}",
                    style = AppTypography.bodySfPro,
                    color = AppColors.lightSub
                )
            }

            Row {
                Text(
                    text = "$${destination.pricePerPerson}/",
                    style = AppTypography.bodySfUi,
                    color = AppColors.primaryBlue
                )

                Text(
                    text = "Person",
                    style = AppTypography.bodySfUi,
                    color = AppColors.lightSub
                )
            }
        }
    }
}


