package com.example.perloti.contacts.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.perloti.contacts.model.entities.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactContract {


    public static final String TABLE = "contact";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    public static final String PHONENUMBER = "phoneNumber";


    public static final String[] COLUMNS = {ID, NAME, SURNAME, AGE, PHONENUMBER};

    public static String getCreateTableScript(){

        final StringBuilder create = new StringBuilder();

        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append(ID + " INTEGER PRIMARY KEY, ");
        create.append(NAME + " TEXT NOT NULL, ");
        create.append(SURNAME + " TEXT NOT NULL, ");
        create.append(AGE + " TEXT NOT NULL, ");
        create.append(PHONENUMBER + " TEXT NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(Contact contact){
        ContentValues values = new ContentValues();
        values.put(ContactContract.ID, contact.getId());
        values.put(ContactContract.NAME, contact.getName());
        values.put(ContactContract.SURNAME, contact.getSurname());
        values.put(ContactContract.AGE, contact.getAge());
        values.put(ContactContract.PHONENUMBER, contact.getPhoneNumber());
        return values;
    }

    public static Contact getContact(Cursor cursor){
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setId(cursor.getLong(cursor.getColumnIndex(ContactContract.ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(ContactContract.NAME)));
            contact.setSurname(cursor.getString(cursor.getColumnIndex(ContactContract.SURNAME)));
            contact.setAge(cursor.getString(cursor.getColumnIndex(ContactContract.AGE)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ContactContract.PHONENUMBER)));
            return contact;
        }else{
            return null;
        }
    }

    public static List<Contact> getListContact(Cursor cursor){
        ArrayList<Contact> contacts = new ArrayList<>();
        while (cursor.moveToNext()){
            contacts.add(getContact(cursor));
        }
        return contacts;
    }



}
