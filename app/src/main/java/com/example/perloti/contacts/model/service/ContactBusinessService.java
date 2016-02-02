package com.example.perloti.contacts.model.service;

import com.example.perloti.contacts.model.entities.Contact;
import com.example.perloti.contacts.model.http.ContactService;
import com.example.perloti.contacts.model.persistence.ContactRepository;

import java.util.List;

/**
 * Created by andre.perloti on 01/02/2016.
 */
public class ContactBusinessService {

    public static List<Contact> findAll() {
        return ContactRepository.getAll();
    }

    public static void save(Contact contact) {
        ContactRepository.save(contact);
    }

    public static void saveUpdate(Contact contact) {
        ContactRepository.saveUpdate(contact);
    }

    public static void delete(Contact contact) {
        ContactRepository.delete(contact.getId());
    }

    public static Contact findContactById(Long id) {
        Contact contactById = ContactRepository.getContactById(id);
        return contactById;
    }

    public static void sincronize() {
        List<Contact> contacts = ContactService.getContact();

        for (Contact contactWEB : contacts) {
            Contact contactDB = findContactById(contactWEB.getId());
            if (contactDB == null) {
                save(contactWEB);
            } else {
                if (!contactDB.equals(contactWEB)) {
                    Contact contactTemp = findContactById(contactWEB.getId());
                    contactWEB.setId(contactTemp.getId());
                    saveUpdate(contactWEB);
                }
            }
        }
    }

}
