package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.Contact;
import de.govhackathon.wvsvcoronatracker.repositories.ContactRepository;
import de.govhackathon.wvsvcoronatracker.services.ContactService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<Contact> getContacts() {
        return IterableUtils.toList(contactRepository.findAll());
    }

    @Override
    public Optional<Contact> getContact(String id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
