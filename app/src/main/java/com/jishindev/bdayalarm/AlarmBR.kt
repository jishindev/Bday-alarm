package com.jishindev.bdayalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log

class AlarmBR : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmBR", "onReceive() called with: context = [$context], intent = [$intent]")
        context?.showSurprise()

    }
}
