package com.ectosense.contactsapp.APIData.base;

import com.ectosense.contactsapp.APIData.api.ContactsAPI;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public abstract class NetworkInitiateFactory {
    protected abstract ContactsAPI initiateHttp();
}
