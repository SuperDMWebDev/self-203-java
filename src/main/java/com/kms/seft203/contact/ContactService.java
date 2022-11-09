package com.kms.seft203.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }
    public Contact findContactById(Long id)
    {
        Optional<Contact> contact = contactRepository.findContactById(id);
        if(contact.isPresent())
        {
            return contact.get();
        }
        return null;
    }
    public Contact createContact(SaveContactRequest request)
    {
        Contact contact = Contact.createContact(request);
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, SaveContactRequest request) {
        Contact contact = Contact.createContact(request);
        contact.setId(id);
        contactRepository.save(contact);
        return contact;
    }

    public void deleteContact(Long id) {
        Contact contact= contactRepository.findContactById(id).orElse(null);
        assert contact != null;// replace if and throw exception
        contactRepository.delete(contact);
    }

}
