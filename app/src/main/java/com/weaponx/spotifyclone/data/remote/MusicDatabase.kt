package com.weaponx.spotifyclone.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.weaponx.spotifyclone.data.entities.Song
import com.weaponx.spotifyclone.util.Constants.SONG_COLLECTION
import kotlinx.coroutines.tasks.await
import timber.log.Timber

private const val TAG = "MusicDatabase"
class MusicDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<Song> {
        return try {
            songCollection.get().await().toObjects(Song::class.java)
        } catch(e: Exception) {
            Log.e(TAG,"Could not retrieve song list")
            e.printStackTrace()
            emptyList()
        }
    }
}