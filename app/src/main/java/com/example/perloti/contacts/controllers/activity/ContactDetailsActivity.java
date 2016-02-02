package com.example.perloti.contacts.controllers.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.perloti.contacts.R;
import com.example.perloti.contacts.model.entities.Contact;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactDetailsActivity extends AppCompatActivity {

    private static final String PARAM_CONTACT = "CONTACT";
    private TextView textViewName;
    private TextView textViewSurname;
    private TextView textViewAge;
    private TextView textViewPhoneNumber;
    private Contact contact;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        initContact();
        bindToolbar();

        bindTextViewNameContact();
        bindTextViewSurnameContact();
        bindTextViewAgeContact();
        bindTextViewPhoneNumberContact();
    }


    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.detail_contact));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initContact() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.contact = extras.getParcelable(PARAM_CONTACT);
        }
        this.contact = contact == null ? new Contact() : this.contact;
    }

    private void bindTextViewPhoneNumberContact() {
        textViewPhoneNumber = (TextView) findViewById(R.id.phoneNumberContact);
        textViewPhoneNumber.setText(contact.getPhoneNumber());
    }

    private void bindTextViewAgeContact() {
        textViewAge = (TextView) findViewById(R.id.ageContact);
        textViewAge.setText(contact.getAge());
    }

    private void bindTextViewSurnameContact() {
        textViewSurname = (TextView) findViewById(R.id.surnameContact);
        textViewSurname.setText(contact.getSurname());
    }

    private void bindTextViewNameContact() {
        textViewName = (TextView) findViewById(R.id.nameContact);
        textViewName.setText(contact.getName());
    }


}
