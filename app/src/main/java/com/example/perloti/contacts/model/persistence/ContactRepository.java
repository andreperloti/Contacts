package com.example.perloti.contacts.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.perloti.contacts.model.entities.Contact;

import java.util.List;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactRepository {

    public static void saveUpdate(Contact contact) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ContactContract.getContentValues(contact);
        if (contact.getId() == null) {
            db.insert(ContactContract.TABLE, null, contentValues);
        } else {
            String where = ContactContract.ID + " =? ";
            String[] parans = {contact.getId().toString()};
            db.update(ContactContract.TABLE, contentValues, where, parans);
        }

        db.close();
        databaseHelper.close();
    }

    public static void save(Contact contact) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = ContactContract.getContentValues(contact);
        db.insert(ContactContract.TABLE, null, contentValues);

        db.close();
        databaseHelper.close();
    }


    public static void delete(Long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = ContactContract.ID + " =? ";
        String[] params = {String.valueOf(id)};
        db.delete(ContactContract.TABLE, where, params);

        db.close();
        databaseHelper.close();
    }


    public static Contact getContactById(Long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = ContactContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};
        Cursor cursor = db.query(ContactContract.TABLE, ContactContract.COLUMNS, where, params, null, null, null);
        Contact contact = ContactContract.getContact(cursor);

        db.close();
        databaseHelper.close();
        return contact;
    }

    public static List<Contact> getAll() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(ContactContract.TABLE, ContactContract.COLUMNS, null, null, null, null, ContactContract.NAME);
        List<Contact> values = ContactContract.getListContact(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }

}
