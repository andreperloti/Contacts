package com.example.perloti.contacts.model.http;

import android.util.Log;

import com.example.perloti.contacts.model.entities.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactService {

    private static final String URL = "http://bit.ly/1iYSORT";

    public static ArrayList<Contact> getContact() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            java.net.URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            Log.i("getContact", "Codigo de retorno da requisicao: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                ObjectMapper objectMapper = new ObjectMapper();
                CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Contact.class);
                contacts = objectMapper.readValue(inputStream, collectionType);
            }

        } catch (Exception e) {
            Log.e(ContactService.class.getName(), "" + e.getMessage());
        }
        return contacts;
    }


}
