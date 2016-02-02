package com.example.perloti.contacts.controllers.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.perloti.contacts.R;
import com.example.perloti.contacts.model.entities.Contact;

import java.util.List;
import java.util.Random;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactListAdapter extends BaseAdapter {

    private Activity context;
    private List<Contact> contacts;

    public ContactListAdapter(Activity context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = context.getLayoutInflater().inflate(R.layout.item_list_contact, viewGroup, false);
        Contact contact = getItem(position);

        TextView textViewName = (TextView) view.findViewById(R.id.nameContact);
        textViewName.setText(contact.getName()+" "+contact.getSurname());

        TextView textViewThumbnailContact = (TextView) view.findViewById(R.id.thumbnailContact);
        char early = contact.getName().charAt(0);
        textViewThumbnailContact.setText(String.valueOf(early));

        bindColorItemList(textViewThumbnailContact);

        return view;
    }

    private void bindColorItemList(TextView textViewThumbnailContact) {
        int[] arrayColors = context.getResources().getIntArray(R.array.androidcolors);
        int color = arrayColors[new Random().nextInt(arrayColors.length)];
        textViewThumbnailContact.setBackgroundColor(color);
    }
}
