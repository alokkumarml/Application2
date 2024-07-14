package com.example.myapplication1.adapter

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.R
import com.example.myapplication1.fragment.addlatest.AddLatestFragment

class MediaAdapter(private val context: Context, private val mediaList: List<AddLatestFragment.MediaItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            VIEW_TYPE_IMAGE -> {
                val view = inflater.inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view)
            }
            VIEW_TYPE_VIDEO -> {
                val view = inflater.inflate(R.layout.item_video, parent, false)
                VideoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mediaItem = mediaList[position]
        when (holder.itemViewType) {
            VIEW_TYPE_IMAGE -> {
                val imageViewHolder = holder as ImageViewHolder
                imageViewHolder.bind(mediaItem)
            }
            VIEW_TYPE_VIDEO -> {
                val videoViewHolder = holder as VideoViewHolder
                videoViewHolder.bind(mediaItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun getItemViewType(position: Int): Int {
        val mediaItem = mediaList[position]
        return if (mediaItem.mimeType.startsWith("video")) {
            VIEW_TYPE_VIDEO
        } else {
            VIEW_TYPE_IMAGE
        }
    }

    // Constants for view types
    private val VIEW_TYPE_IMAGE = 1
    private val VIEW_TYPE_VIDEO = 2

    // ViewHolder for images
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        fun bind(mediaItem: AddLatestFragment.MediaItem) {
            // Load image using Glide or any other image loading library
            Glide.with(itemView)
                .load(mediaItem.uri)
                .into(imageView)
        }
    }

    // ViewHolder for videos
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.video_thumbnail)
        private val duration: TextView = itemView.findViewById(R.id.duration)

        fun bind(mediaItem: AddLatestFragment.MediaItem) {
            // Load video thumbnail or placeholder image for video
//            val videoThumbnail = getVideoThumbnail(mediaItem.uri)

           // imageView.setImageURI(mediaItem.uri)

            Glide.with(itemView)
                .load(mediaItem.uri)
                .into(imageView)

            duration.text = mediaItem.duration?.let { formatDuration(it) }

        }

        fun formatDuration(duration: Long): String {
            val minutes = duration / 1000 / 60
            val seconds = (duration / 1000) % 60
            return String.format("%d:%02d", minutes, seconds)
        }


//        private fun getVideoThumbnail(uri: Uri): Bitmap? {
//            // Implement logic to retrieve video thumbnail
//            // For simplicity, you can use ThumbnailUtils or Glide to load video thumbnails
//            return uri.path?.let { ThumbnailUtils.createVideoThumbnail(it, MediaStore.Images.Thumbnails.MINI_KIND) }
//        }
    }
}
