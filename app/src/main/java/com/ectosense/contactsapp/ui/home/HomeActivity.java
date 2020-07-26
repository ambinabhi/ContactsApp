package com.ectosense.contactsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.ui.addcontact.AddContactActivity;
import com.ectosense.contactsapp.utils.view.CaSnackBar;
import com.ectosense.contactsapp.utils.view.ProgressUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView{

    TextView textNoContacts;
    RecyclerView recyclerViewContacts;
    Toolbar toolbar;

    HomePresenter homePresenter;
    ContactListAdapter mContactsAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Contact> mSectionContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textNoContacts = (TextView) findViewById(R.id.tv_no_contact);
        recyclerViewContacts = (RecyclerView) findViewById(R.id.rv_contacts);

        setUpToolBar(toolbar);
        homePresenter = new HomePresenterImpl(this);

        textNoContacts.setVisibility(View.GONE);
    }

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact");
            getSupportActionBar().setIcon(R.drawable.ic_contacts);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item_done = menu.findItem(R.id.action_done);
        if (item_done != null) {
            item_done.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add){
                gotoAddContactActivity();
        }
        return true;
    }

    private void gotoAddContactActivity() {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }


    private void getHeaderListLatter(ArrayList<Contact> contactList) {
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact user1, Contact user2) {
                return String.valueOf(user1.getFirstname().charAt(0)).toUpperCase().compareTo(String.valueOf(user2.getFirstname().charAt(0)).toUpperCase());
            }
        });

        String lastHeader = "";

        int size = contactList.size();

        for (int i = 0; i < size; i++) {

            Contact user = contactList.get(i);
            String header = String.valueOf(user.getFirstname().charAt(0)).toUpperCase();

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;
                mSectionContactList.add(new Contact(
                        header, true
                ));
            }

            mSectionContactList.add(user);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressUtils.showProgress(this, "Loading Contacts");
        homePresenter.getAllContacts(this);
    }


    @Override
    public void onError(String error) {
        ProgressUtils.hideProgress();
        recyclerViewContacts.setVisibility(View.GONE);
        textNoContacts.setVisibility(View.VISIBLE);
        CaSnackBar.showLong(getWindow().getDecorView(), error);
    }

    @Override
    public void onSuccess(List<Contact> contactList) {
        ArrayList<Contact> mContactList = new ArrayList<>(contactList);
        ArrayList<Contact> mContactsModel = new ArrayList<>();
        for (int j = 0; j < mContactList.size() ; j++) {
            mContactList.get(j).setSection(false);
            mContactsModel.add(mContactList.get(j));
        }
        mSectionContactList = new ArrayList<>();
        getHeaderListLatter(mContactsModel);
        ProgressUtils.hideProgress();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerViewContacts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewContacts.setLayoutManager(mLayoutManager);
        mContactsAdapter = new ContactListAdapter(this, mSectionContactList);
        recyclerViewContacts.setAdapter(mContactsAdapter);
    }


}
