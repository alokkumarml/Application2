package com.example.myapplication1.fragment.uploadsubmit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings.TextSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.util.TimeUtils.formatDuration
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentAddLatestBinding
import com.example.myapplication1.fragment.add.AddFragment
import kotlinx.serialization.json.JsonNull.content
import okhttp3.internal.concurrent.formatDuration
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class UploadSubmitFragment : Fragment() {

    companion object {
        lateinit var selecturi: MutableState<Set<AddFragment.MediaItem>>

        fun newInstance() = UploadSubmitFragment()
    }

    private lateinit var viewModel: UploadSubmitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    //    return inflater.inflate(R.layout.fragment_upload_submit, container, false)

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {

                    GalleryScreen()
                }
            }
        }


    }


    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showBackground = true)
    @Composable
    fun GalleryScreen() {
        val context = LocalContext.current
        val images = rememberSaveable { selecturi.value.toList() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(images) { imageUri ->
                    ImageCard(imageUri)
                }
            }

            Text(
                text = "upload",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                    .padding(5.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }


    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun ImageCard(mediaItem: AddFragment.MediaItem) {
        Card(
            modifier = Modifier
                .padding(4.dp) // Add some padding between items
                .fillMaxWidth()
                .aspectRatio(1f) // Ensure square aspect ratio
        ) {

            when {
                mediaItem.mimeType.startsWith("image") -> {
                    Image(
                        painter = rememberImagePainter(mediaItem.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                mediaItem.mimeType.startsWith("video") -> {


                    val imageLoader = ImageLoader.Builder(LocalContext.current)
                        .components { add(VideoFrameDecoder.Factory()) }
                        .build()

                    Box(
                        modifier = Modifier.run {
                            fillMaxWidth()
                                .height(200.dp)
                                .padding(2.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = mediaItem.uri, // <========== Pass video path here
                            imageLoader = imageLoader,
                            contentDescription = "Video Thumbnail",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(4.dp),
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
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(5.dp)
                            )
                        }
                    }



                }
            }


        }
    }

}