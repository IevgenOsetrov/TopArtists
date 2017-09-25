package com.android.topartists.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.topartists.R;
import com.bumptech.glide.Glide;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class Utils {

    /**
     * Check internet connection
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToNetwork(final Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    public static ImageView showToolbarInfo(final AppCompatActivity activity, String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        TextView titleTextView = toolbar.findViewById(R.id.toolbar_title);
        titleTextView.setText(title);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView indicator = toolbar.findViewById(R.id.indicator);

        if (Utils.isConnectedToNetwork(activity)) {
            Glide.with(activity).load(R.drawable.green_indi).into(indicator);
        } else {
            Glide.with(activity).load(R.drawable.red_indi).into(indicator);
        }

        ImageButton backButton = toolbar.findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                activity.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            }
        });

        return indicator;
    }
}
