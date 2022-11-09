package com.kms.seft203.report;

import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactRepository;
import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.dashboard.DashboardRepository;
import com.kms.seft203.task.TaskRepository;
import com.kms.seft203.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private TaskRepository taskRepository;

    private List<Object> countFieldOfTask(String field, User user) {
        if ("isCompleted".equals(field)) {

            return Collections.singletonList(taskRepository.findAllCompletion(user.getId())); // oruse List.copyOf
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot find field of tasks");
    }

    public List<Object> countFieldOfContact(String field) {
        switch (field) {

            case "firstname": {
                return contactRepository.findAll().stream().map(Contact::getFirstName).collect(Collectors.toList());
            }
            case "lastname": {
                return contactRepository.findAll().stream().map(Contact::getLastName).collect(Collectors.toList());
            }
            case "title": {
                return contactRepository.findAll().stream().map(Contact::getTitle).collect(Collectors.toList());
            }
            case "department": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getDepartment)
                        .collect(Collectors.toList());
            }
            case "project": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getProject)
                        .collect(Collectors.toList());
            }
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot find field of contact");
        }
    }

    private List<Object> countFieldsOfDashboard(String fieldName, User user) {
        switch (fieldName) {
            case "title": {
                return dashboardRepository.findAllByUser_Id(user.getId())
                        .stream()
                        .map(Dashboard::getTitle)
                        .collect(Collectors.toList());
            }
            case "layouttype": {
                return dashboardRepository.findAllByUser_Id(user.getId())
                        .stream()
                        .map(Dashboard::getLayoutType)
                        .collect(Collectors.toList());
            }
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot find field of dashboard");
        }
    }


    public Map<String,Integer> countFieldByCollection (String collection, String field, User user)
    {
        String lowerCollection= collection.toLowerCase(Locale.ROOT);
        List<Object> list;
        switch(lowerCollection)
        {
            case "task":
            {
                list = countFieldOfTask(field,user);
                break;
            }
            case "dashboard":
            {
                list=countFieldsOfDashboard(field,user);
                break;
            }
            case "contact":
            {
                list = countFieldOfContact(field);
                break;
            }
            default :
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Cannot find collection which you passed in");
        }
        return list.stream().collect(
                Collectors.groupingBy(
                        Object:: toString,
                        Collectors.reducing(0,(element)-> 1,Integer::sum) // number of element
                )
        );
    }

}
