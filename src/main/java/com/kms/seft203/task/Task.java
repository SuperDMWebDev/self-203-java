package com.kms.seft203.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kms.seft203.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="task",schema="public")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="task")
    private String task;

    @Column(name="is_completed")
    private Boolean isCompleted;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public static Task of(SaveTaskRequest request,User user) {
        return new Task(0L,request.getTask(),false, user);
    }
}
