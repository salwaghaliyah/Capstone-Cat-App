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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ViewModelFactory
import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.data.response.MakananItem
import com.dicoding.capstonecat.data.response.VitaminItem
import com.dicoding.capstonecat.di.Injection
import com.dicoding.capstonecat.ui.common.UiState
import com.dicoding.capstonecat.ui.components.CatItem

@Composable
fun DetailScreen(
    breed: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateBack: () -> Unit

) {

    var isCatFav by remember { mutableStateOf(false) }

    viewModel.catFav.collectAsState(UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getFavCatByBreed(breed)
            }

            is UiState.Success -> {
                isCatFav = it.data == 1
            }

            else -> {
                isCatFav = false
            }
        }
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getCatByBreed(breed)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    imgUrl = data.kucing ?: "",
                    breed = data.ras ?: "Unknown",
                    desc = data.deskripsi ?: "Unknown",
                    imgMknUrl = data.makanan!!,
                    perawatan = data.perawatan ?: "Unknown",
                    imgVitUrl = data.vitamin!!,
                    onBackClick = navigateBack,
                    onFavoriteClick = {
                        if (isCatFav) {
                            viewModel.deleteFavCat(data.ras!!)
                        } else {
                            val catEntity = CatFavEntity(
                                breed = data.ras ?: "Unknown",
                                imgUrl = data.kucing ?: ""
                            )
                            viewModel.saveFavCat(catEntity)
                        }
                    },
                    isCatFav = isCatFav
                )
            }
            else -> ""
        }
    }

}

@Composable
fun DetailContent(
    imgUrl: String,
    breed: String,
    desc: String,
    imgMknUrl: List<MakananItem?>,
    perawatan: String,
    imgVitUrl: List<VitaminItem?>,
    onBackClick: () -> Unit,
    isCatFav: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val iconColor = if (isCatFav) remember { mutableStateOf(Color.Red) } else remember {
        mutableStateOf(Color.Unspecified)
    }
    val iconVector = if (isCatFav) remember { mutableStateOf(Icons.Default.Favorite) } else remember {
        mutableStateOf(Icons.Default.FavoriteBorder)
    }

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
                        .clickable {
                            onFavoriteClick()
                            if (isCatFav) {
                                iconColor.value = Color.Unspecified
                                iconVector.value = Icons.Default.FavoriteBorder
                            } else {
                                iconColor.value = Color.Red
                                iconVector.value = Icons.Default.Favorite
                            }
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = iconVector.value,
                        tint = iconColor.value,
                        contentDescription = "Favorite",
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
                        text = breed,
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
                        items(imgMknUrl.size) { index ->
                            CatItem(
                                imageUrl = imgMknUrl[index]?.image!!,
                                name = imgMknUrl[index]?.nama!!
                            )
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
                        items(imgVitUrl.size) { index ->
                            CatItem(
                                imageUrl = imgVitUrl[index]?.image!!,
                                name = imgVitUrl[index]?.nama!!
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}


//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun DetailContentPreview() {
//    CapstoneCatTheme {
//        DetailContent(
//            imgUrl = "https://s3-alpha-sig.figma.com/img/4041/f9ec/4e24bf6dc3fe057328c6c4ff71fe4312?Expires=1701648000&Signature=F035k3z57rxjx7nieKmaZD~vH-c2G0RrFHU5YKh0hEMwl~y9pjPLkoBvZT-55BHtT2k6UULKJkRVrkVSkI7xvQdiDgDvanccYUqpAYvTNTKjjHm9lSjmcWJbT6pPjozSO-dfXg1O9yoh8RPh44x1eO5zUChBcGGDYRRPIqciAJ7zyoOCMx5hRYULxk0Rd9eBcs-ERLBFNNnqH1Ear8ZpupH4F1i-J6-jqb-Gs6kQu5jRqZSA9bka50AbZp9tvW9a9zGRhBQb9sv-nBH9Ockveistn801NhaO9cnaFMdoQPXfw5mINicPyFLhTM-QozK6m6oig2utlF5Cm1R2y1zS5A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
//            breed = "Scottish Fold",
//            desc = "Kucing Persia memiliki ciri fisik yang khas, seperti wajah bulat dengan hidung pesek, mata besar yang bulat, telinga kecil yang berjumbai, dan bulu yang panjang, lebat, dan halus. Bulunya datar dan mengalir secara alami, dan ada variasi warna yang luas pada bulu mereka.",
//            imgMknUrl = "",
//            perawatan = "Bulu panjang dan tebal mereka memerlukan perawatan rutin yang teratur, termasuk sikat bulu setiap hari untuk mencegah kekusutan dan pembentukan gumpalan bulu. Selain itu, perlu menjaga kebersihan mata dan telinga mereka.",
//            imgVitUrl = "",
//            onBackClick = {},
//            onFavoriteClick = {},
//            isCatFav = false
//        )
//    }
//}
