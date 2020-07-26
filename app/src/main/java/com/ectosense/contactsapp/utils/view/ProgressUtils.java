package com.ectosense.contactsapp.utils.view;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class ProgressUtils {
    private static ProgressDialog mProgressDialog =  null;

    public static void showProgress(Activity activity, String message) {
        mProgressDialog = new ProgressDialog(activity);

        mProgressDialog.setCancelable(false);

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public static void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
