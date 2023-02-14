package com.weaponx.spotifyclone.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weaponx.spotifyclone.exoplayer.MusicService
import com.weaponx.spotifyclone.exoplayer.MusicServiceConnection
import com.weaponx.spotifyclone.exoplayer.currentPlaybackPosition
import com.weaponx.spotifyclone.util.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    musicServiceConnection: MusicServiceConnection
): ViewModel()  {
    private val playbackState = musicServiceConnection.playBackState

    private val _currSongDuration = MutableLiveData<Long>()
    val currSongDuration: LiveData<Long> = _currSongDuration

    private val _currPlayerPosition = MutableLiveData<Long>()
    val currPlayerPosition: LiveData<Long> = _currPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while(true) {
                val pos = playbackState.value?.currentPlaybackPosition
                if(currPlayerPosition.value != pos) {
                    _currPlayerPosition.postValue(pos!!)
                    _currSongDuration.postValue(MusicService.currentSongDuration)
                }
                //So the coroutine can be cancelled, and is slightly more performant
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}