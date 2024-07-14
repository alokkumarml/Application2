package com.example.myapplication1.fragment.add

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val eventChannel = Channel<NoteListEvent>()



//    fun adddata(context: Context){
//
//        fetchMedia(context)
//
//    }
//
//
//
//
//    fun fetchMedia(context: Context) {
//
//        val mediaList = mutableListOf<AddFragment.MediaItem>()
//        val projection = arrayOf(
//            MediaStore.MediaColumns._ID,
//            MediaStore.MediaColumns.DATE_ADDED,
//            MediaStore.MediaColumns.MIME_TYPE,
//            MediaStore.MediaColumns.DATA,
//            MediaStore.MediaColumns.DURATION
//        )
//
//
//        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
//        val queryUri = MediaStore.Files.getContentUri("external")
//
//        val cursor = context.contentResolver.query(queryUri, projection, null, null, sortOrder)
//        cursor?.use {
//
//            var thumbnail: Bitmap? = null
//
//            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
//            val dateColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
//            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
//
//            while (it.moveToNext()) {
//                val id = it.getLong(idColumn)
//                val dateAdded = it.getLong(dateColumn)
//                val mimeType = it.getString(mimeColumn)
//                val contentUri = ContentUris.withAppendedId(queryUri, id)
//
//                if (it.getString(mimeColumn).startsWith("video/")){
//
//
//                    val dataColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//                    val durationColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DURATION)
//                    val filePath = it.getString(dataColumn)
//                    val duration = it.getLong(durationColumn)
//
//                    thumbnail = ThumbnailUtils.createVideoThumbnail(
//                        filePath,
//                        MediaStore.Images.Thumbnails.MINI_KIND
//                    )
//                    Log.d("fetchVideoThumbnails", "Thumbnail for $filePath: $thumbnail")
//
////                    mediaList.add(
////                        AddFragment.MediaItem(
////                            contentUri,
////                            thumbnail,
////                            duration,
////                            mimeType,
////                            dateAdded
////                        )
////                    )
//
////                    viewModelScope.launch {
////                        eventChannel.send(NoteListEvent.AddMedia( AddFragment.MediaItem(
////                            contentUri,
////                            thumbnail,
////                            duration,
////                            mimeType,
////                            dateAdded
////                        )))
////                    }
//
//
//                }else {
//
////                    mediaList.add(
////                        AddFragment.MediaItem(
////                            contentUri,
////                            thumbnail,
////                            0,
////                            mimeType,
////                            dateAdded
////                        )
////                    )
////                    viewModelScope.launch {
////                        eventChannel.send(NoteListEvent.AddMedia( AddFragment.MediaItem(
////                            contentUri,
////                            thumbnail,
////                            0,
////                            mimeType,
////                            dateAdded
////                        )))
////                    }
//
//
//
//                }
//            }
//        }
//       // return mediaList
//    }


    sealed class NoteListEvent {
        data class AddMedia(val mediaitem: AddFragment.MediaItem) : NoteListEvent()
    }



}



//fun fetchMedia(context: Context): List<AddFragment.MediaItem> {
//
//    val mediaList = mutableListOf<AddFragment.MediaItem>()
//    val projection = arrayOf(
//        MediaStore.MediaColumns._ID,
//        MediaStore.MediaColumns.DATE_ADDED,
//        MediaStore.MediaColumns.MIME_TYPE,
//        MediaStore.MediaColumns.DATA,
//        MediaStore.MediaColumns.DURATION
//    )
//
//
//    val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
//    val queryUri = MediaStore.Files.getContentUri("external")
//
//    val cursor = context.contentResolver.query(queryUri, projection, null, null, sortOrder)
//    cursor?.use {
//
//        var thumbnail: Bitmap? = null
//
//        val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
//        val dateColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
//        val mimeColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
//
//        while (it.moveToNext()) {
//            val id = it.getLong(idColumn)
//            val dateAdded = it.getLong(dateColumn)
//            val mimeType = it.getString(mimeColumn)
//            val contentUri = ContentUris.withAppendedId(queryUri, id)
//
//            if (it.getString(mimeColumn).startsWith("video/")){
//
//
//                val dataColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//                val durationColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DURATION)
//                val filePath = it.getString(dataColumn)
//                val duration = it.getLong(durationColumn)
//
//                thumbnail = ThumbnailUtils.createVideoThumbnail(
//                    filePath,
//                    MediaStore.Images.Thumbnails.MINI_KIND
//                )
//                Log.d("fetchVideoThumbnails", "Thumbnail for $filePath: $thumbnail")
//
//                    mediaList.add(
//                        AddFragment.MediaItem(
//                            contentUri,
//                            thumbnail,
//                            duration,
//                            mimeType,
//                            dateAdded
//                        )
//                    )
//
//
//
//
//            }else {
//
//                    mediaList.add(
//                        AddFragment.MediaItem(
//                            contentUri,
//                            thumbnail,
//                            0,
//                            mimeType,
//                            dateAdded
//                        )
//                    )
//
//
//
//            }
//        }
//    }
//    return mediaList
//}