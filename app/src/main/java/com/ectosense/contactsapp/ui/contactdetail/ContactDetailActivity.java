package com.ectosense.contactsapp.ui.contactdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.constants.Constants;
import com.ectosense.contactsapp.utils.view.CaSnackBar;

public class ContactDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    Contact contactDetails;
    TextView textPhone;
    TextView textEmail;
    TextView textName;
    TextView textError;
    LinearLayout layoutHeader;
    LinearLayout layoutDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        textPhone = (TextView)findViewById(R.id.tv_mobile);
        textEmail = (TextView)findViewById(R.id.tv_email);
        textName = (TextView)findViewById(R.id.tv_name);
        textError = (TextView)findViewById(R.id.tv_no_details);
        layoutHeader = (LinearLayout)findViewById(R.id.ll_profile_header);
        layoutDetail = (LinearLayout)findViewById(R.id.ll_profile_detail);

        setUpToolBar(toolbar);

        if (getIntent().getExtras() != null) {
            textError.setVisibility(View.GONE);
            contactDetails = getIntent().getParcelableExtra(Constants.EXTRA_CONTACT_DETAIL);
            if(contactDetails != null){
                textPhone.setText(contactDetails.getPhone());
                textEmail.setText(contactDetails.getEmail());
                textName.setText(String.format("%s %s", contactDetails.getFirstname(), contactDetails.getLastname()));
            }else{
                handleError();
            }
        }else{
            handleError();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void handleError() {
        textError.setVisibility(View.VISIBLE);
        layoutHeader.setVerticalGravity(View.GONE);
        layoutDetail.setVerticalGravity(View.GONE);
        CaSnackBar.showLong(getWindow().getDecorView(), "Could not load contact details");
    }
}
