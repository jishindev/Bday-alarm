package com.jishindev.bdayalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootBR : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BootBR", "onReceive() called with: context = [$context], intent = [$intent]")
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            context?.setSurprise()
        }
    }
}