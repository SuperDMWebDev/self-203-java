package com.kms.seft203.task;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class SaveTaskRequest  {
    @NotBlank(message = "Task must not empty")
    private String task;

    public SaveTaskRequest()
    {
        System.out.println("No args passed in ");
    }

    public SaveTaskRequest(String task)
    {
        System.out.println("All args passed in ");
        this.task = task;
    }
}
