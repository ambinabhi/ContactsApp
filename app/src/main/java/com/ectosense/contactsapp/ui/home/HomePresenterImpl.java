package com.ectosense.contactsapp.ui.home;

import androidx.annotation.NonNull;

import com.ectosense.contactsapp.APIData.api.ContactsAPI;
import com.ectosense.contactsapp.APIData.base.NetworkInitiateSingleton;
import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.constants.Constants;
import com.ectosense.contactsapp.utils.network.ConnectionUtils;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;


/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView mHomeView;
    HomePresenterImpl(HomeView homeView) {
        mHomeView = homeView;
    }

    @Override
    public void getAllContacts(HomeActivity homeActivity) {

        if(ConnectionUtils.isConnectedToNetwork(homeActivity)){
            ContactsAPI restInterface = NetworkInitiateSingleton.getInstance().initiateHttp();

            restInterface.getAllContacts()
                    .observeOn(mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this.responseHandler, this.errorHandler);
        }else{
            mHomeView.onError(Constants.NO_INTERNET_CONNECTION);
        }
    }

    private Consumer<List<Contact>> responseHandler = new Consumer<List<Contact>>() {
        @Override
        public void accept(List<Contact> contactList) {
            if(contactList.size() > 0){
                mHomeView.onSuccess(contactList);
            }else{
                mHomeView.onError(Constants.NO_CONTACTS);
            }

        }
    };

    private Consumer<Throwable> errorHandler = new Consumer<Throwable>() {
        @Override
        public void accept(@NonNull Throwable throwable) {
           mHomeView.onError("Something Went Wrong");
        }
    };
}

