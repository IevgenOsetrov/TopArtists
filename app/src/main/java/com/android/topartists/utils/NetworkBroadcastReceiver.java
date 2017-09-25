package com.android.topartists.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.topartists.model.NetworkDisableMessage;
import com.android.topartists.model.NetworkEnableMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evgeniy on 03-Sep-17.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            EventBus.getDefault().post(new NetworkEnableMessage());
        } else {
            EventBus.getDefault().post(new NetworkDisableMessage());
        }
    }
}
