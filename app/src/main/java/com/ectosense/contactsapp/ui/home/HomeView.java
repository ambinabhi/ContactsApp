package com.ectosense.contactsapp.ui.home;

import com.ectosense.contactsapp.APIData.model.Contact;

import java.util.List;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
interface HomeView {
    void onError(String error);

    void onSuccess(List<Contact> contactList);
}
