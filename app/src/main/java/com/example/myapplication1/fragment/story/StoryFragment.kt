package com.example.myapplication1.fragment.story

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myapplication1.fragment.add.AddFragment
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.MediaController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import com.example.myapplication1.R
import com.example.myapplication1.fragment.addmusic.AddMusicFragment
import com.example.myapplication1.fragment.uploadsubmit.UploadSubmitFragment

class StoryFragment : Fragment() {

    companion object {


        lateinit var selecturistory: AddFragment.MediaItem

        fun newInstance() = StoryFragment()
    }

    private lateinit var viewModel: StoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return inflater.inflate(R.layout.fragment_story, container, false)


        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {

                    GalleryScreen1212()
                }
            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showBackground = true)
    @Composable
    fun GalleryScreen1212() {
        val context = LocalContext.current
        val images = remember { selecturistory }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                androidx.compose.material3.Button(
                    onClick = {

                        val verificationCodeFragment = AddMusicFragment()
                        val transactionmanager = parentFragmentManager.beginTransaction()
                        transactionmanager.replace(R.id.fragment_containerhome, verificationCodeFragment)
                        transactionmanager.addToBackStack(null)
                        transactionmanager.commit()


                    }
                ) {
                    Text("Add Music")

                }
                androidx.compose.material3.Button(
                    onClick = {}
                ) {

                    Text("Upload")

                }
            }

//            Text(
//                text = "upload",
//                textAlign = TextAlign.Center,
//                fontSize = 20.sp,
//                color = Color.White,
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(40.dp)
//                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
//                    .padding(5.dp)
//                    .align(alignment = Alignment.End)
//            )

            ImageCard(images)

        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun ImageCard(mediaItem: AddFragment.MediaItem) {
        Card(
            modifier = Modifier
                .padding(4.dp) // Add some padding between items
                .fillMaxWidth()
            
        ) {

            when {
                mediaItem.mimeType.startsWith("image") -> {
                    Image(
                        painter = rememberImagePainter(mediaItem.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                mediaItem.mimeType.startsWith("video") -> {

                    VideoPlayer(mediaItem.uri)

                }
            }


        }
    }


    @Composable
    fun VideoPlayer(videoUri: Uri) {
        var isPlaying by remember { mutableStateOf(false) }
        var duration by remember { mutableStateOf(0) }
        var currentPosition by remember { mutableStateOf(0) }
        val context = LocalContext.current

        val mediaController = remember {
            MediaController(context).apply {
                setAnchorView(VideoView(context))
            }
        }

        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoURI(videoUri)
                    start()
                    setOnPreparedListener { mediaPlayer ->
                        duration = mediaPlayer.duration
                    }
                    setOnCompletionListener {
                        isPlaying = false
                    }
                    setMediaController(mediaController)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Slider(
                value = currentPosition.toFloat(),
                onValueChange = { /* Optional: you can perform some action here if needed */ },
                valueRange = 0f..duration.toFloat(),
                onValueChangeFinished = {
                    //mediaController.seek(it.toInt())
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formatTime(currentPosition),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                IconButton(onClick = {
                    isPlaying = !isPlaying
                    if (isPlaying) {
                        mediaController.show()
                    } else {
                        mediaController.hide()
                    }
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.CheckCircle else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }
                Text(
                    text = formatTime(duration),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    private fun formatTime(ms: Int): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}