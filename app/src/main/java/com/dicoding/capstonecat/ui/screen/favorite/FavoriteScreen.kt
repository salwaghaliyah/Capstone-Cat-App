package com.dicoding.capstonecat.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ViewModelFactory
import com.dicoding.capstonecat.di.Injection
import com.dicoding.capstonecat.ui.components.CatItem
import com.dicoding.capstonecat.ui.components.ScrollToTopButton
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier : Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (Int) -> Unit
) {
    FavoriteContent(navigateToDetail = navigateToDetail)
}


@Composable
fun FavoriteContent(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
   Box(
       modifier = modifier
   ) {
       val scope = rememberCoroutineScope()
       val gridState = rememberLazyGridState()
       val showButton: Boolean by remember {
           derivedStateOf { gridState.firstVisibleItemIndex > 2 }
       }

       LazyVerticalGrid(
           state = gridState,
           columns = GridCells.Adaptive(130.dp),
           contentPadding = PaddingValues(10.dp),
           horizontalArrangement = Arrangement.spacedBy(12.dp),
           verticalArrangement = Arrangement.spacedBy(10.dp),
           modifier = modifier
               .fillMaxSize()
               .padding(horizontal = 12.dp)
       ) {
           item(
               span = { GridItemSpan(maxLineSpan) }
           ) {
               Text(
                   text = stringResource(R.string.fav_prolog),
                   style = TextStyle(
                       fontSize = 24.sp,
                       lineHeight = 36.sp,
                       fontFamily = FontFamily(Font(R.font.mrexbold)),
                       fontWeight = FontWeight(800),
                   ),
                   modifier = Modifier
                       .padding(vertical = 10.dp)
               )
           }
           items(20) { index ->
               CatItem(
                   imageUrl = "https://s3-alpha-sig.figma.com/img/4041/f9ec/4e24bf6dc3fe057328c6c4ff71fe4312?Expires=1701648000&Signature=F035k3z57rxjx7nieKmaZD~vH-c2G0RrFHU5YKh0hEMwl~y9pjPLkoBvZT-55BHtT2k6UULKJkRVrkVSkI7xvQdiDgDvanccYUqpAYvTNTKjjHm9lSjmcWJbT6pPjozSO-dfXg1O9yoh8RPh44x1eO5zUChBcGGDYRRPIqciAJ7zyoOCMx5hRYULxk0Rd9eBcs-ERLBFNNnqH1Ear8ZpupH4F1i-J6-jqb-Gs6kQu5jRqZSA9bka50AbZp9tvW9a9zGRhBQb9sv-nBH9Ockveistn801NhaO9cnaFMdoQPXfw5mINicPyFLhTM-QozK6m6oig2utlF5Cm1R2y1zS5A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
                   name = "ScottishFold $index",
                   modifier = Modifier.clickable {
                       navigateToDetail(1)
                   }
               )
           }
       }
       AnimatedVisibility(
           visible = showButton,
           enter = fadeIn() + slideInVertically(),
           exit = fadeOut() + slideOutVertically(),
           modifier = Modifier
               .padding(bottom = 10.dp)
               .align(Alignment.BottomCenter)
       ) {
           ScrollToTopButton(
               onClick = {
                   scope.launch {
                       gridState.scrollToItem(index = 0)
                   }
               }
           )
       }
   }
}

@Preview(showBackground = true)
@Composable
fun FavoriteContentPreview() {
    CapstoneCatTheme {
        FavoriteContent(navigateToDetail = {})
    }
}

