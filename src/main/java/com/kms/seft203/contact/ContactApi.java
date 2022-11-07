package com.kms.seft203.contact;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVWriter;

import javax.persistence.Column;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(contactService.findAllContacts());
    }

    @GetMapping("/export")
    public void exportCSVContacts(@RequestBody String path)
    {
        //get column name
        List<String> listColumnNames = new ArrayList<String>();
        Field[] fields= Contact.class.getDeclaredFields();
        for(Field field : fields)
        {
            Column column = field.getAnnotation(Column.class);
            if(column!=null)
            {
                listColumnNames.add(column.name());
            }
        }
        String [] columnNames = new String[listColumnNames.size()];
        listColumnNames.toArray(columnNames);
        //get all contacts
        List<Contact> contacts = contactService.findAllContacts();
        List<String[]> listContacts  = new ArrayList<>();
        contacts.forEach(contact -> {
            String[] StringOfContacts = new String[]{contact.getFirstName(),
                    contact.getLastName(), contact.getTitle(), contact.getDepartment(), contact.getProject(), contact.getAvatar(),String.valueOf(contact.getEmployeeId())};

            listContacts.add(StringOfContacts);
        });
        try{
            FileWriter writer = new FileWriter(path.trim()+"/contact-output.csv");
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(columnNames);
            for(String[] contact: listContacts)
            {
                csvWriter.writeNext(contact);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(contactService.findContactById(id));
    }

    @PostMapping("/import")
    public void writeContact (@RequestBody String path) throws IOException // path of file csv
    {
        path = path.trim() +"/contact-input.csv";
        Path file = Paths.get(path);
        if(Files.notExists(file))
        {
            File newFile = new File(path);
        }
        CSVReader csvReader = new CSVReader(new FileReader(path));
        List<String[]> listContacts = new ArrayList<String[]>();
        String [] nextLine ;
        csvReader.readNext(); // first row contains column name
        while((nextLine = csvReader.readNext()) != null)
        {
            listContacts.add(nextLine);
        }
        for(String[] contact: listContacts)
        {
            SaveContactRequest saveContactRequest = new SaveContactRequest(contact[0],contact[1],contact[2],contact[3],contact[4],contact[5],Integer.parseInt(contact[6]));
            contactService.createContact(saveContactRequest);
        }

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
