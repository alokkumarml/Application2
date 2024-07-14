package com.example.myapplication1.fragment.addlatest

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication1.adapter.MediaAdapter
import com.example.myapplication1.databinding.FragmentAddLatestBinding

class AddLatestFragment : Fragment() {

    companion object {
        fun newInstance() = AddLatestFragment()
    }

    private lateinit var viewModel: AddLatestViewModel

    lateinit var binding: FragmentAddLatestBinding


    private lateinit var mediaAdapter: MediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLatestBinding.inflate(inflater, container, false)



        binding.recyclerview.layoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.VERTICAL)

        // Fetch media items
        val mediaList = fetchMedia(requireContext())

        // Initialize adapter
        mediaAdapter = MediaAdapter(requireContext(), mediaList)

        // Set adapter to RecyclerView
        binding.recyclerview.adapter = mediaAdapter



        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddLatestViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun fetchMedia(context: Context): List<MediaItem> {
        val mediaList = mutableListOf<MediaItem>()
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        val queryUri = MediaStore.Files.getContentUri("external")

        val cursor = context.contentResolver.query(queryUri, projection, null, null, sortOrder)
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn)
                val contentUri = ContentUris.withAppendedId(queryUri, id)

                // Check if the media is a video and fetch its duration
                val duration = if (mimeType.startsWith("video/")) {
                    getVideoDuration(context, contentUri)
                } else {
                    null
                }


                mediaList.add(MediaItem(contentUri, dateAdded, mimeType,duration))
            }
        }
        return mediaList
    }

    data class MediaItem(val uri: Uri, val dateAdded: Long, val mimeType: String,val duration: Long? = null)
    private fun getVideoDuration(context: Context, uri: Uri): Long? {
        val projection = arrayOf(
            MediaStore.Video.VideoColumns.DURATION
        )

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)
            if (cursor.moveToFirst()) {
                return cursor.getLong(durationColumn)
            }
        }
        return null
    }
}