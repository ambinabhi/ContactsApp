package com.ectosense.contactsapp.APIData.api;

import com.ectosense.contactsapp.APIData.model.Contact;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public interface ContactsAPI {

    @GET("/contacts")
    Observable<List<Contact>> getAllContacts();

    @POST("/contacts")
    Observable<ResponseBody> createNewContract(@Body Contact contact);
}
