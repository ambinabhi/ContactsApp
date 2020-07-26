package com.ectosense.contactsapp.APIData.base;

import com.ectosense.contactsapp.APIData.api.ContactsAPI;
import com.ectosense.contactsapp.constants.ConstantsURL;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class NetworkInitiateSingleton extends NetworkInitiateFactory {

    private static NetworkInitiateSingleton ourInstance = new NetworkInitiateSingleton();

    private NetworkInitiateSingleton() {
    }


    public static NetworkInitiateSingleton getInstance() {
        return ourInstance;
    }

    @Override
    public ContactsAPI initiateHttp() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantsURL.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ///making object of RestAdapter
        return retrofit.create(ContactsAPI.class);
    }
}
