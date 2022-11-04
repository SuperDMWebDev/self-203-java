package com.kms.seft203.contact;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(contactService.findAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(contactService.findContactById(id));
    }

    @PostMapping
    public ResponseEntity<Contact> create(@RequestBody SaveContactRequest request) {
        if(ObjectUtils.isNotEmpty(request))
        {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(contactService.createContact(request));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody SaveContactRequest request) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(contactService.updateContact(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
