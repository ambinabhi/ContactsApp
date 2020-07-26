package com.ectosense.contactsapp.APIData.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class Contact implements Parcelable {

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("favorite")
    @Expose
    private Boolean favorite;

    private Boolean isSection;
    private String header;

    public Contact(String header, boolean isSection) {
        this.header = header;
        this.isSection = isSection;
    }

    public Contact(String firstName, String lastName, String phone, String email) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.phone = phone;
        this.email = email;
    }

    protected Contact(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        phone = in.readString();
        byte tmpFavorite = in.readByte();
        favorite = tmpFavorite == 0 ? null : tmpFavorite == 1;
        byte tmpIsSection = in.readByte();
        isSection = tmpIsSection == 0 ? null : tmpIsSection == 1;
        header = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getSection() {
        return isSection;
    }

    public void setSection(Boolean section) {
        isSection = section;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeByte((byte) (favorite == null ? 0 : favorite ? 1 : 2));
        parcel.writeByte((byte) (isSection == null ? 0 : isSection ? 1 : 2));
        parcel.writeString(header);
    }
}
