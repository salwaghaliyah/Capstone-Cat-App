package com.dicoding.capstonecat.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.di.Injection
import com.dicoding.capstonecat.ui.components.CatItem
import com.dicoding.capstonecat.ui.components.ScrollToTopButton
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    HomeContent(viewModel, navigateToDetail = navigateToDetail, modifier)

    LaunchedEffect(true) {
        // Panggil fungsi getAllCats() saat HomeScreen diinisialisasi
        viewModel.getAllCatsFromApi()
    }
}


@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cats: List<Cat> by viewModel.cats.observeAsState(emptyList())
    val query by viewModel.query

    Box(modifier = modifier) {        val scope = rememberCoroutineScope()
        val gridState = rememberLazyGridState()
        val showButton: Boolean by remember {
            derivedStateOf { gridState.firstVisibleItemIndex > 4 }
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
                    text = stringResource(R.string.home_prolog),
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        fontFamily = FontFamily(Font(R.font.mrexbold)),
                        fontWeight = FontWeight(800),
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(size = 14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 190.dp),
                    elevation = CardDefaults.cardElevation(12.dp),
                ) {
                    Box(Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(R.string.text_card_home),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        lineHeight = 22.sp,
                                        fontFamily = FontFamily(Font(R.font.mrbold)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000),
                                    ),
                                    modifier = Modifier.wrapContentSize()
                                )
                            }

                            Image(
                                painter = painterResource(R.drawable.whitecat2),
                                contentDescription = "cat_picture",
                                modifier = Modifier
                                    .height(140.dp)
                                    .width(140.dp) // Sesuaikan ukuran gambar
                            )
                        }
                    }
                }



            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = { newQuery ->
                        viewModel.search(newQuery)
                    },
                )
            }

            items(cats) { cat ->
                CatItem(
                    imageUrl = cat.Kucing,
                    name = cat.Ras,
                    modifier = Modifier.clickable {
                        navigateToDetail(cat.Ras)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
  ) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_cat))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
    ) {
    }
}

@Composable
@Preview (showBackground = true)
fun HomeContentPreview() {
    CapstoneCatTheme {
        val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
        )
        //HomeContent(viewModel)
    }
}