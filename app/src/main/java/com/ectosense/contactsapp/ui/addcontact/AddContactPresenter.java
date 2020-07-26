package com.ectosense.contactsapp.ui.addcontact;

import android.app.Activity;

/**
 * Created by Abhilash MB on 26-07-2020.
 */
public interface AddContactPresenter {

    void addNewContact(Activity activity, String firstName, String lastName, String phone, String email);
}
