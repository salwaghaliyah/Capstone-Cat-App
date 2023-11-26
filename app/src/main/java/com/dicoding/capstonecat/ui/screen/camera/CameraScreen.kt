package com.dicoding.capstonecat.ui.screen.camera

import android.Manifest
import android.content.Context
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.dicoding.capstonecat.R
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

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            capturedImageUri = uri
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


    if (capturedImageUri.path?.isNotEmpty() == true || imageUri?.path?.isNotEmpty() == true)
    {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = rememberImagePainter(capturedImageUri),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = rememberImagePainter(imageUri),
            contentDescription = null
        )
    }
    else
    {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = painterResource(id = R.drawable.ic_image),
            contentDescription = null
        )
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