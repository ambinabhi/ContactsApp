package com.ectosense.contactsapp.ui.contactdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.constants.Constants;
import com.ectosense.contactsapp.utils.view.CaSnackBar;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Contact contactDetails;
    TextView textPhone;
    TextView textEmail;
    TextView textName;
    TextView textError;
    LinearLayout layoutHeader;
    LinearLayout layoutDetail;

    ImageView imageMessage;
    ImageView imagePhone;
    ImageView imageMail;


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
        imageMessage = (ImageView)findViewById(R.id.iv_message);
        imagePhone = (ImageView)findViewById(R.id.iv_call);
        imageMail = (ImageView)findViewById(R.id.iv_mail);

        imageMessage.setOnClickListener(this);
        imagePhone.setOnClickListener(this);
        imageMail.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_message:
                openMessagingApp();
                break;
            case R.id.iv_call:
                openCallApp();
                break;
            case R.id.iv_mail:
                openMailApp();
                break;
        }
    }

    private void openMailApp() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contactDetails.getEmail()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private void openCallApp() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contactDetails.getPhone()));
        startActivity(intent);
    }

    private void openMessagingApp() {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + contactDetails.getPhone()));
        startActivity(smsIntent);
    }
}
