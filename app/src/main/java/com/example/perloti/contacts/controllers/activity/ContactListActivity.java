package com.example.perloti.contacts.controllers.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.perloti.contacts.R;
import com.example.perloti.contacts.controllers.adapter.ContactListAdapter;
import com.example.perloti.contacts.model.entities.Contact;
import com.example.perloti.contacts.model.service.ContactBusinessService;

import java.util.List;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listviewContact;
    private List<Contact> contacts;
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        bindToolbar();
        //new DownloadContact().execute();

        bindListViewContact();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_download:
                downloadContacts();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadContacts() {
        new DownloadContact().execute();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    private void bindListViewContact() {
        listviewContact = (ListView) findViewById(R.id.listViewContact);
        onUpdate();
        listviewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                contact = (Contact) adapter.getItemAtPosition(position);
                Intent intent = new Intent(ContactListActivity.this, ContactDetailsActivity.class);
                intent.putExtra("CONTACT", contact);
                startActivity(intent);
            }
        });
        listviewContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                ContactListAdapter adapter = (ContactListAdapter) listviewContact.getAdapter();
                contact = adapter.getItem(position);
                return false;
            }
        });
        registerForContextMenu(listviewContact);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contact_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemClicado = item.getItemId();
        switch (itemClicado) {
            case (R.id.menuCall):
                callTo();
                break;
            case (R.id.menuDelete):
                onMenuDeleteClick();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void onMenuDeleteClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lbl_confirm);
        builder.setMessage(R.string.msg_confirm_delete);
        builder.setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DeleteContact().execute();
                String msg = getString(R.string.msg_contato_delete_success);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        builder.setNeutralButton(R.string.lbl_no, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void callTo() {
        final Intent goToSOPhoneCall = new Intent(Intent.ACTION_CALL);
        goToSOPhoneCall.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
        startActivity(goToSOPhoneCall);
    }

    private void onUpdate() {
        contacts = ContactBusinessService.findAll();
        listviewContact.setAdapter(new ContactListAdapter(ContactListActivity.this, contacts));
    }

    // **************** AsyncTask DOWNLOAD *******************

    private class DownloadContact extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(ContactListActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(getString(R.string.Contacts));
            progressDialog.setMessage(getString(R.string.Loading));
            progressDialog.setIcon(R.mipmap.ic_launcher);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ContactBusinessService.sincronize();
            return null;
        }

        @Override
        protected void onPostExecute(Void products) {
            super.onPostExecute(products);
            progressDialog.setMessage(ContactListActivity.this.getString(R.string.msg_complete));
            progressDialog.dismiss();
            onUpdate();
        }

    }


    // **************** AsyncTask DELETE *******************
    private class DeleteContact extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(ContactListActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(getString(R.string.Contacts));
            progressDialog.setMessage(getString(R.string.Loading));
            progressDialog.setIcon(R.mipmap.ic_launcher);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ContactBusinessService.delete(contact);
            return null;
        }

        @Override
        protected void onPostExecute(Void products) {
            super.onPostExecute(products);
            progressDialog.setMessage(ContactListActivity.this.getString(R.string.msg_complete));
            progressDialog.dismiss();
            onUpdate();
        }

    }


}
