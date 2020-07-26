package com.ectosense.contactsapp.ui.addcontact;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ectosense.contactsapp.APIData.api.ContactsAPI;
import com.ectosense.contactsapp.APIData.base.NetworkInitiateSingleton;
import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.constants.Constants;
import com.ectosense.contactsapp.utils.network.ConnectionUtils;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Abhilash MB on 26-07-2020.
 */
class AddContactPresenterImpl implements AddContactPresenter {

    private AddContactView mAddContactView;

    AddContactPresenterImpl(AddContactView addContactView) {
            this.mAddContactView = addContactView;
    }

    @Override
    public void addNewContact(Activity activity, String firstName, String lastName, String phone, String email) {

        if(firstName.trim().length() == 0){
            mAddContactView.onError(Constants.ENTER_FIRST_NAME);
        } else if(lastName.trim().length() == 0){
            mAddContactView.onError(Constants.ENTER_LAST_NAME);
        } else if(phone.trim().length() == 0 ){
            mAddContactView.onError(Constants.ENTER_PHONE);
        } else if(email.trim().length() == 0){
            mAddContactView.onError(Constants.ENTER_EMAIL);
        }else{
            if(ConnectionUtils.isConnectedToNetwork(activity)){
                ContactsAPI restInterface = NetworkInitiateSingleton.getInstance().initiateHttp();
                Contact contact = new Contact(firstName, lastName, phone, email);
                restInterface.createNewContract(contact)
                        .observeOn(mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this.responseHandler, this.errorHandler);
            }else {
                mAddContactView.onError(Constants.NO_INTERNET_CONNECTION);
            }
        }
    }

    private Consumer<ResponseBody> responseHandler = new Consumer<ResponseBody>() {
        @Override
        public void accept(ResponseBody responseBody) {
            Log.d("Create Contact Error", responseBody.toString());
            mAddContactView.onSuccess();

        }
    };

    private Consumer<Throwable> errorHandler = new Consumer<Throwable>() {
        @Override
        public void accept(@NonNull Throwable throwable) {
            mAddContactView.onError("Something Went Wrong");
        }
    };

}
