package com.dicoding.capstonecat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme

@Composable
fun CatItem(
    imageUrl: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(145.16.dp)
            .height(183.8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card (
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .shadow(6.dp),
                elevation = CardDefaults.cardElevation(72.dp),

            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(135.dp)
                        .padding(horizontal = 20.dp)
                        .padding(top = 12.dp)
                )
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)),
                    ) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mrbold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 11.dp, vertical = 13.dp)
                    )
                }
            }

        }
    }
}


@Preview(showBackground = false)
@Composable
fun CatItemPreview() {
    CapstoneCatTheme {
        CatItem(
            imageUrl = "https://s3-alpha-sig.figma.com/img/4041/f9ec/4e24bf6dc3fe057328c6c4ff71fe4312?Expires=1701648000&Signature=F035k3z57rxjx7nieKmaZD~vH-c2G0RrFHU5YKh0hEMwl~y9pjPLkoBvZT-55BHtT2k6UULKJkRVrkVSkI7xvQdiDgDvanccYUqpAYvTNTKjjHm9lSjmcWJbT6pPjozSO-dfXg1O9yoh8RPh44x1eO5zUChBcGGDYRRPIqciAJ7zyoOCMx5hRYULxk0Rd9eBcs-ERLBFNNnqH1Ear8ZpupH4F1i-J6-jqb-Gs6kQu5jRqZSA9bka50AbZp9tvW9a9zGRhBQb9sv-nBH9Ockveistn801NhaO9cnaFMdoQPXfw5mINicPyFLhTM-QozK6m6oig2utlF5Cm1R2y1zS5A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            name = "Scottish Fold"
        )
    }
}