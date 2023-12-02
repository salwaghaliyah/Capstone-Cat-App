package com.dicoding.capstonecat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sampleItem = ItemDetail(
            title = "Kucing Persia",
            description = "Kucing Persia memiliki ciri fisik yang khas, seperti wajah bulat dengan hidung pesek, mata besar yang bulat, telinga kecil yang berjumbai, dan bulu yang panjang, lebat, dan halus. Bulunya datar dan mengalir secara alami, dan ada variasi warna yang luas pada bulu mereka.",
            title2 = "Cara Merawat Kucing",
            tutor = "Bulu panjang dan tebal mereka memerlukan perawatan rutin yang teratur, termasuk sikat bulu setiap hari untuk mencegah kekusutan dan pembentukan gumpalan bulu. Selain itu, perlu menjaga kebersihan mata dan telinga mereka.",
            imageResId = R.drawable.cat // Ganti dengan gambar yang Anda miliki di drawable
        )

        val favoriteItems = listOf(
            ItemDetail("Favorit 1", "", "", "", R.drawable.cat),
            ItemDetail("Favorit 2", "", "", "", R.drawable.cat),
            ItemDetail("Favorit 3", "", "", "", R.drawable.cat)
        )

        setContent {
            CapstoneCatTheme {
                DetailActivity(itemDetail = sampleItem, favoriteItems = favoriteItems)
            }
        }
    }
}

// Model untuk detail item
data class ItemDetail(
    val title: String,
    val description: String,
    val title2: String,
    val tutor: String,
    val imageResId: Int)

// Activity untuk menampilkan detail
@Composable
fun DetailActivity(itemDetail: ItemDetail, favoriteItems: List<ItemDetail>) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(id = itemDetail.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = itemDetail.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = itemDetail.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = itemDetail.title2,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = itemDetail.tutor,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Makanan Favorit",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                content = {
                    items(favoriteItems) { favoriteItem ->
                        FavoriteContentCard(favoriteItem)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Rekomendasi Vitamin",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                content = {
                    items(favoriteItems) { favoriteItem ->
                        VitaminContentCard(favoriteItem)
                    }
                }
            )
        }
    }
}

// Card untuk konten favorit
@Composable
fun FavoriteContentCard(itemDetail: ItemDetail) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(200.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp)),

    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = itemDetail.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itemDetail.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun VitaminContentCard(itemDetail: ItemDetail) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(200.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp)),


        ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = itemDetail.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itemDetail.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

