package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    /**
     * Get all contacts
     *
     * @return List of all contacts, empty collection if none available
     */
    List<Contact> getContacts();

    /**
     * Get a contact by id
     *
     * @return contacts or null
     */
    Optional<Contact> getContact(String id);

    /**
     * Create a contact
     *
     * @param contact data to create contact from
     * @return Created contact
     */
    Contact createContact(final Contact contact);


    /**
     * Update a contact
     *
     * @param contact contact data to be changed
     * @return Updated contact
     */
    Contact updateContact(final Contact contact);

}
