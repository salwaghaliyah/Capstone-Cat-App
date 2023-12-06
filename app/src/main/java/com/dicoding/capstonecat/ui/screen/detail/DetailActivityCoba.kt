package com.dicoding.capstonecat.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ViewModelFactory
import com.dicoding.capstonecat.di.Injection
import com.dicoding.capstonecat.ui.components.CatItem
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme

@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateBack: () -> Unit

) {

    // buat nampilin data api
//    val catDetail by viewModel.catDetail.observeAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.getCatDetail("Persia")
//    }
//
//    catDetail?.let { detail ->
//        DetailContent(
//            imgUrl = detail.Images,
//            title = detail.Ras,
//            desc = detail.Deskripsi,
//            imgMknUrl = "https://s3-alpha-sig.figma.com/img/e324/839f/bf06be697ec432f008b5e32a75a1c3d7?Expires=1702252800&Signature=JOIGXIOwm9nk4E3Ci0dCDalDk-ullh~V7AFhGlOBr3BHL6BlQeo~iwdQboelrSYvte2a~H~pMWzqgTvcjeIV3EVgeGBQk3AMz7V3EjSz8u3iTLolTZrZr3yfo~u~4Neag7MwYJ9y09JBhPEhtZcP9VVZrAYt~WjLtq4ibYAGi85OkYjFnkXIEgZu0kf7pWSq0zs1URrPXnBRblPl2DfabRgxskbgK2b0wfruw1mrjBxHayTR5S2eHLJ6EYqUWWtkjbMgFTY1XlPQofEA-gaUj7VIy0RoTcXBUQbSac1j3~WF3wJi3eAyJXAnIgEvFRsJaocU3Dwf3X41U8nQik4FIQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
//            perawatan = detail.Perawatan,
//            makananFav = "Whiskas",
//            vitamin = "Fish collagen",
//            onBackClick = navigateBack,
//        )
//    }

    DetailContent(
        imgUrl = "https://s3-alpha-sig.figma.com/img/4041/f9ec/4e24bf6dc3fe057328c6c4ff71fe4312?Expires=1701648000&Signature=F035k3z57rxjx7nieKmaZD~vH-c2G0RrFHU5YKh0hEMwl~y9pjPLkoBvZT-55BHtT2k6UULKJkRVrkVSkI7xvQdiDgDvanccYUqpAYvTNTKjjHm9lSjmcWJbT6pPjozSO-dfXg1O9yoh8RPh44x1eO5zUChBcGGDYRRPIqciAJ7zyoOCMx5hRYULxk0Rd9eBcs-ERLBFNNnqH1Ear8ZpupH4F1i-J6-jqb-Gs6kQu5jRqZSA9bka50AbZp9tvW9a9zGRhBQb9sv-nBH9Ockveistn801NhaO9cnaFMdoQPXfw5mINicPyFLhTM-QozK6m6oig2utlF5Cm1R2y1zS5A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
        title = "Scottish Fold",
        desc = "Kucing Persia memiliki ciri fisik yang khas, seperti wajah bulat dengan hidung pesek, mata besar yang bulat, telinga kecil yang berjumbai, dan bulu yang panjang, lebat, dan halus. Bulunya datar dan mengalir secara alami, dan ada variasi warna yang luas pada bulu mereka.",
        imgMknUrl = "https://s3-alpha-sig.figma.com/img/e324/839f/bf06be697ec432f008b5e32a75a1c3d7?Expires=1702252800&Signature=JOIGXIOwm9nk4E3Ci0dCDalDk-ullh~V7AFhGlOBr3BHL6BlQeo~iwdQboelrSYvte2a~H~pMWzqgTvcjeIV3EVgeGBQk3AMz7V3EjSz8u3iTLolTZrZr3yfo~u~4Neag7MwYJ9y09JBhPEhtZcP9VVZrAYt~WjLtq4ibYAGi85OkYjFnkXIEgZu0kf7pWSq0zs1URrPXnBRblPl2DfabRgxskbgK2b0wfruw1mrjBxHayTR5S2eHLJ6EYqUWWtkjbMgFTY1XlPQofEA-gaUj7VIy0RoTcXBUQbSac1j3~WF3wJi3eAyJXAnIgEvFRsJaocU3Dwf3X41U8nQik4FIQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
        perawatan = "Bulu panjang dan tebal mereka memerlukan perawatan rutin yang teratur, termasuk sikat bulu setiap hari untuk mencegah kekusutan dan pembentukan gumpalan bulu. Selain itu, perlu menjaga kebersihan mata dan telinga mereka.",
        makananFav = "Whiskas",
        vitamin = "Fish collagen",
        onBackClick = navigateBack,
    )
}

@Composable
fun DetailContent(
    imgUrl: String,
    title: String,
    desc: String,
    imgMknUrl: String,
    perawatan: String,
    makananFav: String,
    vitamin: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .verticalScroll(rememberScrollState())
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .padding(horizontal = 20.dp)

                ) {
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = "image detail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(130.dp)
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {}
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = Color.Red,
                        contentDescription = "Bookmark",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(45.dp, 45.dp, 0.dp, 0.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 24.dp)
                ){
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 36.sp,
                            fontFamily = FontFamily(Font(R.font.mrexbold)),
                            fontWeight = FontWeight(800),
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.detail_desc_title),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mrbold)),
                            fontWeight = FontWeight(600),
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    Text(
                        text = desc ,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = FontFamily(Font(R.font.mrmedium)),
                            fontWeight = FontWeight(300),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Justify,
                        )
                    )
                    Text(
                        text = stringResource(R.string.detail_maintenance_title),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mrbold)),
                            fontWeight = FontWeight(600),
                        ),
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )
                    Text(
                        text = perawatan ,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = FontFamily(Font(R.font.mrmedium)),
                            fontWeight = FontWeight(300),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,

                            textAlign = TextAlign.Justify,
                        )
                    )
                    Text(
                        text = stringResource(R.string.detail_food_title),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mrbold)),
                            fontWeight = FontWeight(600),
                        ),
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(end = 16.dp),
                    ) {
                        items(5) { index ->
                            CatItem(
                                imageUrl = imgMknUrl,
                                name = makananFav + " "+ index)
                        }
                    }
                    Text(
                        text = stringResource(R.string.detail_vit_title),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mrbold)),
                            fontWeight = FontWeight(600),
                        ),
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(end = 16.dp),
                    ) {
                        items(5) { index ->
                            CatItem(
                                imageUrl = imgMknUrl,
                                name = vitamin
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    CapstoneCatTheme {
        DetailContent(
            imgUrl = "https://s3-alpha-sig.figma.com/img/4041/f9ec/4e24bf6dc3fe057328c6c4ff71fe4312?Expires=1701648000&Signature=F035k3z57rxjx7nieKmaZD~vH-c2G0RrFHU5YKh0hEMwl~y9pjPLkoBvZT-55BHtT2k6UULKJkRVrkVSkI7xvQdiDgDvanccYUqpAYvTNTKjjHm9lSjmcWJbT6pPjozSO-dfXg1O9yoh8RPh44x1eO5zUChBcGGDYRRPIqciAJ7zyoOCMx5hRYULxk0Rd9eBcs-ERLBFNNnqH1Ear8ZpupH4F1i-J6-jqb-Gs6kQu5jRqZSA9bka50AbZp9tvW9a9zGRhBQb9sv-nBH9Ockveistn801NhaO9cnaFMdoQPXfw5mINicPyFLhTM-QozK6m6oig2utlF5Cm1R2y1zS5A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            title = "Scottish Fold",
            desc = "Kucing Persia memiliki ciri fisik yang khas, seperti wajah bulat dengan hidung pesek, mata besar yang bulat, telinga kecil yang berjumbai, dan bulu yang panjang, lebat, dan halus. Bulunya datar dan mengalir secara alami, dan ada variasi warna yang luas pada bulu mereka.",
            imgMknUrl = "https://s3-alpha-sig.figma.com/img/e324/839f/bf06be697ec432f008b5e32a75a1c3d7?Expires=1702252800&Signature=JOIGXIOwm9nk4E3Ci0dCDalDk-ullh~V7AFhGlOBr3BHL6BlQeo~iwdQboelrSYvte2a~H~pMWzqgTvcjeIV3EVgeGBQk3AMz7V3EjSz8u3iTLolTZrZr3yfo~u~4Neag7MwYJ9y09JBhPEhtZcP9VVZrAYt~WjLtq4ibYAGi85OkYjFnkXIEgZu0kf7pWSq0zs1URrPXnBRblPl2DfabRgxskbgK2b0wfruw1mrjBxHayTR5S2eHLJ6EYqUWWtkjbMgFTY1XlPQofEA-gaUj7VIy0RoTcXBUQbSac1j3~WF3wJi3eAyJXAnIgEvFRsJaocU3Dwf3X41U8nQik4FIQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            perawatan = "Bulu panjang dan tebal mereka memerlukan perawatan rutin yang teratur, termasuk sikat bulu setiap hari untuk mencegah kekusutan dan pembentukan gumpalan bulu. Selain itu, perlu menjaga kebersihan mata dan telinga mereka.",
            makananFav = "Whiskas",
            vitamin = "Fish collagen",
            onBackClick = {}
        )
    }
}
