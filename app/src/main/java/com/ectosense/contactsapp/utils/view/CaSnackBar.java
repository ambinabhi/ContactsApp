package com.ectosense.contactsapp.utils.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class CaSnackBar {

    private static String TAG = "CaSnackBar";

    public static void showLong(View contextView, @Nullable CharSequence text) {
        show(contextView, text, Snackbar.LENGTH_LONG);
    }

    public static void showShort(View contextView, @Nullable CharSequence text) {
        show(contextView, text, Snackbar.LENGTH_SHORT);
    }

    private static void show(View contextView, @Nullable CharSequence text, int duration) {
        if (contextView != null && !TextUtils.isEmpty(text)) {
            try {
                Snackbar.make(contextView, text, duration).show();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }

}
