package com.ectosense.contactsapp.ui.addcontact;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.utils.view.CaSnackBar;
import com.ectosense.contactsapp.utils.view.ProgressUtils;

public class AddContactActivity extends AppCompatActivity implements AddContactView {

    Toolbar toolbar;
    EditText editFirstName;
    EditText editLastName;
    EditText editPhone;
    EditText editEmail;

    AddContactPresenter addContactPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editFirstName = (EditText) findViewById(R.id.et_first_name);
        editLastName = (EditText) findViewById(R.id.et_last_name);
        editPhone = (EditText) findViewById(R.id.et_mobile);
        editEmail = (EditText) findViewById(R.id.et_email);

        setUpToolBar(toolbar);

        addContactPresenter = new AddContactPresenterImpl(this);
    }

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Contact");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item_add = menu.findItem(R.id.action_add);
        if (item_add != null) {
            item_add.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            addNewContact();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewContact() {
        ProgressUtils.showProgress(this, "Adding Contact");
        addContactPresenter.addNewContact(this,editFirstName.getText().toString().trim(), editLastName.getText().toString().trim(),
                editPhone.getText().toString().trim(), editEmail.getText().toString().trim());
    }

    @Override
    public void onError(String errorMessage) {
        ProgressUtils.hideProgress();
        CaSnackBar.showLong(getWindow().getDecorView(), errorMessage);
    }

    @Override
    public void onSuccess() {
        ProgressUtils.hideProgress();
        CaSnackBar.showLong(getWindow().getDecorView(), "Contact added Successfully");
        editFirstName.getText().clear();
        editLastName.getText().clear();
        editPhone.getText().clear();
        editEmail.getText().clear();
        editFirstName.clearFocus();
        editLastName.clearFocus();
        editPhone.clearFocus();
        editEmail.clearFocus();
    }
}
