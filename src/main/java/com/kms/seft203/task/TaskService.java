package com.kms.seft203.task;

import com.kms.seft203.user.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getOneTask(Long id) {
        Task task = taskRepository.findTaskById(id).orElse(null);
        return task;
    }

    public Task createTask(SaveTaskRequest request, User user) {
        Task task = Task.of(request,user);
        if(ObjectUtils.isNotEmpty(task))
        {
            taskRepository.save(task);
            return task;
        }
        return null;
    }


    public Task updateTask(Long id, SaveTaskRequest request,User user) {
        Task task = Task.of(request,user);
        task.setId(id);
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findTaskById(id).orElse(null);
        if(task != null)
        {
            taskRepository.deleteTaskById(task.getId());
        }
    }
}
