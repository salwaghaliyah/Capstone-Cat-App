package com.dicoding.capstonecat.ui.screen.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.dicoding.capstonecat.DetailActivity
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ui.theme.Purple80
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier
) {
    imageCaptureFromCamera()
}

@Composable
fun imageCaptureFromCamera()
{
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )


    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    var isImageCaptured by remember { mutableStateOf(false) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                capturedImageUri = uri
                isImageCaptured = true // Menandakan gambar berhasil diambil
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            capturedImageUri = uri
            isImageCaptured = true // Menandakan gambar berhasil diambil
        }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it)
        {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }
        else
        {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top, // Letakkan di bagian atas
        horizontalAlignment = Alignment.CenterHorizontally, // Posisikan di tengah horizontal

    ) {
        Text(
            text = "Scan Your Cat Here !!",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontFamily = FontFamily(Font(R.font.mrexbold)),
                fontWeight = FontWeight(800),
                color = Color(0xFF3F3E3F),
            )
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        FloatingActionButton(
            onClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED)
                {
                    cameraLauncher.launch(uri)
                }
                else
                {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            contentColor = Color.Black
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "camera")
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,

        ) {

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        FloatingActionButton(
            onClick = { launcher.launch("image/*") }) {
            Icon(painter = painterResource(id = R.drawable.ic_image), contentDescription = "galery")
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 180.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
//                val intent = Intent(context, DetailActivity::class.java)
//                context.startActivity(intent)
                      },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.run { buttonColors(Purple80) },
            enabled = isImageCaptured // Tombol hanya dapat di-klik jika gambar telah diambil
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.scan), // Ganti dengan sumber daya gambar yang sesuai
                    contentDescription = "Icon", // Deskripsi konten (untuk aksesibilitas)
                    modifier = Modifier.size(26.dp) // Atur ukuran ikon sesuai kebutuhan
                )
                Spacer(modifier = Modifier.width(8.dp)) // Spasi antara ikon dan teks
                Text(
                    text = "SCAN",
                    style = TextStyle(
                        fontSize = 26.sp,
                        lineHeight = 36.sp,
                        fontFamily = FontFamily(Font(R.font.mrexbold)),
                        fontWeight = FontWeight(800),
                        color = Color(0xFF3F3E3F),
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (capturedImageUri.path?.isNotEmpty() == true)
        {
            Image(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .height(430.dp)
                    .size(1000.dp, 400.dp),
                painter = rememberImagePainter(capturedImageUri),
                contentDescription = null
            )
        }
        else
        {
            Image(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .height(500.dp)
                    .size(1200.dp, 500.dp),
                painter = painterResource(id = R.drawable.kucing),
                contentDescription = null
            )
        }

    }



}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}