package com.example.myapplication1.fragment.add

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.videoFrameMillis
import com.example.myapplication1.R
import com.example.myapplication1.fragment.story.StoryFragment
import com.example.myapplication1.fragment.uploadsubmit.UploadSubmitFragment


class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            // Handle permission requests results
            // See the permission example in the Android platform samples: https://github.com/android/platform-samples

            if (results.all { it.value }) {
                // All permissions are granted, proceed to load images
                loadImages()
            } else {
                // Permission is denied, show a message to the user
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }


        }

    companion object {
        fun newInstance() = AddFragment()
    }

   // private lateinit var viewModel: AddViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_add, container, false)

        return ComposeView(requireContext()).apply {
            setContent {
                MyApp {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Load and display images
                        loadImages()
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            requestPermissions.launch(
                                arrayOf(
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                                )
                            )
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestPermissions.launch(
                                arrayOf(
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_VIDEO
                                )
                            )
                        } else {
                            requestPermissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                        }

                    }
                }
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadImages() {
        (requireView() as ComposeView).setContent {
            MyApp {

                GalleryScreen()

            }
        }
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        MaterialTheme {
            Surface {
                content()
            }
        }
    }

    enum class ContentType{
        IMAGES,
        VIDEOS,
        ALL
    }


    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun ImageCard(
        mediaItem: MediaItem,
        isSelected: Boolean,
        onSelect: (Uri) -> Unit
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp) // Add some padding between items
                .fillMaxWidth()
                .aspectRatio(1f) // Ensure square aspect ratio
                .clickable { onSelect(mediaItem.uri) } // Handle selection
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    color = if (isSelected) Color.Red else Color.Transparent, // Change color as needed
                    shape = RoundedCornerShape(2.dp) // 2 dp rounded corners
                )
        ) {
            Box {
                when {
                    mediaItem.mimeType.startsWith("image") -> {
                        Image(
                            painter = rememberImagePainter(mediaItem.uri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    mediaItem.mimeType.startsWith("video") -> {
                        val imageLoader = ImageLoader.Builder(LocalContext.current)
                            .components { add(VideoFrameDecoder.Factory()) }
                            .build()

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = mediaItem.uri,
                                imageLoader = imageLoader,
                                contentDescription = "Video Thumbnail",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Display duration text overlay
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = formatDuration(mediaItem.duration),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                                        .padding(5.dp)
                                )
                            }
                        }
                    }
                }

                // Highlight selected item
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x66000000)) // Semi-transparent overlay
                    )
                }
            }
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun PreviewImageCard() {
        val sampleUri = Uri.parse("content://media/external/images/media/12345")
        val sampleMediaItem = MediaItem(
            uri = sampleUri,
            mimeType = "image/jpeg",
            duration = 0

        )

        ImageCard(
            mediaItem = sampleMediaItem,
            isSelected = true,
            onSelect = {

            }
        )
    }




    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun GalleryScreen() {

        val context = LocalContext.current
        val mediaItems = rememberSaveable { fetchMedia(context) }
//        val selectedItems = remember { mutableStateOf<Set<Uri>>(emptySet()) }

        val selectedItems = rememberSaveable { mutableStateOf<Set<MediaItem>>(emptySet()) }

        val selectedItemsButton = rememberSaveable {  mutableStateOf(0) }

        val isAnyItemSelected = remember {
            derivedStateOf { selectedItems.value.isNotEmpty() }
        }


        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {

                    selectedItemsButton.value = 0

                },
                    colors = if (selectedItemsButton.value==0) ButtonDefaults.buttonColors(Color.Green) else ButtonDefaults.buttonColors(Color.Blue)) {
                    Text("Upload")

                }
                Button(onClick = {

                    selectedItemsButton.value = 1
                    selectedItems.value = emptySet()

                }, colors = if (selectedItemsButton.value==1) ButtonDefaults.buttonColors(Color.Green) else ButtonDefaults.buttonColors(Color.Blue)) {

                    Text("Story")

                }

                if (isAnyItemSelected.value) {
                    Button(onClick = { /* Your Add button logic here */


                         UploadSubmitFragment.selecturi = selectedItems
                        val verificationCodeFragment = UploadSubmitFragment()
                        val transactionmanager = parentFragmentManager.beginTransaction()
                        transactionmanager.replace(R.id.fragment_containerhome, verificationCodeFragment)
                        transactionmanager.addToBackStack(null)
                        transactionmanager.commit()

                    }) {
                        Text("Add")
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 items per row
                modifier = Modifier.padding(8.dp)
            ) {
                items(mediaItems) { mediaItem ->
                    val isSelected = selectedItems.value.contains(mediaItem)
                    ImageCard(
                        mediaItem = mediaItem,
                        isSelected = isSelected,
                        onSelect = { uri ->

                            if(selectedItemsButton.value == 0) {
                                selectedItems.value = if (isSelected) {
                                    selectedItems.value - mediaItem
                                } else {
                                    selectedItems.value + mediaItem
                                }
                            }else {

                                // Next Page

                                StoryFragment.selecturistory = mediaItem
                                val verificationCodeFragment = StoryFragment()
                                val transactionmanager = parentFragmentManager.beginTransaction()
                                transactionmanager.replace(R.id.fragment_containerhome, verificationCodeFragment)
                                transactionmanager.addToBackStack(null)
                                transactionmanager.commit()

                            }

                        }
                    )
                }
            }
        }
    }





    data class MediaItem(val uri: Uri, val duration: Long, val mimeType: String,
                         var isSelected: Boolean = false) // Add this property


    fun formatDuration(duration: Long): String {
        val minutes = duration / 1000 / 60
        val seconds = (duration / 1000) % 60
        return String.format("%d:%02d", minutes, seconds)
    }

 @SuppressLint("SuspiciousIndentation")
 fun fetchMedia(context: Context): List<AddFragment.MediaItem> {

    val mediaList = mutableListOf<AddFragment.MediaItem>()
    val projection = arrayOf(
        MediaStore.MediaColumns._ID,
        MediaStore.MediaColumns.DATE_ADDED,
        MediaStore.MediaColumns.MIME_TYPE,
        MediaStore.MediaColumns.DATA,
        MediaStore.MediaColumns.DURATION
     )


    val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
    val queryUri = MediaStore.Files.getContentUri("external")

    val cursor = context.contentResolver.query(queryUri, projection, null, null, sortOrder)
    cursor?.use {

        val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
       // val dateColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
        val mimeColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)

        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
           // val dateAdded = it.getLong(dateColumn)
            val mimeType = it.getString(mimeColumn)
            val contentUri = ContentUris.withAppendedId(queryUri, id)

            if (it.getString(mimeColumn).startsWith("video/")){


               // val dataColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val durationColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DURATION)
              //  val filePath = it.getString(dataColumn)
                val duration = it.getLong(durationColumn)

                    mediaList.add(
                        AddFragment.MediaItem(
                            contentUri,
                            duration,
                            mimeType
                        )
                    )




            }else {

                    mediaList.add(
                        AddFragment.MediaItem(
                            contentUri,
                            0,
                            mimeType
                        )
                    )



            }
        }
    }
    return mediaList
}

}


