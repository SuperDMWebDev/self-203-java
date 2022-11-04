package com.kms.seft203.contact;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="contact",schema="public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="first_name",length = 30)
    private String firstName;

    @Column(name="last_name",length = 50)
    private String lastName;

    @Column(name="title",length = 30)
    private String title;

    @Column(name="department",length = 30)
    private String department;

    @Column(name="project",length = 30)
    private String project;

    @Column(name="avatar")
    private String avatar;

    @Column(name="employee_id")
    private Integer employeeId;

//    public Contact(String firstName, String lastName, String title, String department, String project, String avatar, Integer employeeId) {
//        this.firstName= firstName;
//    }


    public static Contact createContact(SaveContactRequest request)
    {
        return new Contact(0L,request.getFirstName(),request.getLastName(),request.getTitle(),request.getDepartment(),request.getProject(),request.getAvatar(),request.getEmployeeId());

    }

}
