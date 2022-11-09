package com.kms.seft203.task;

import com.kms.seft203.user.CustomUserDetails;
import com.kms.seft203.user.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskApi {

    private static final Map<String, Task> DATA = new HashMap<>();

    @Autowired
    private TaskService taskService;
    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(taskService.getOneTask(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody SaveTaskRequest request) {
        if(ObjectUtils.isNotEmpty(request))
        {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(taskService.createTask(request,user));
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody SaveTaskRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(taskService.updateTask(id,request,user));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();

    }
}
