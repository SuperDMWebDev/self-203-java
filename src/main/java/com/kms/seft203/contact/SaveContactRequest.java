package com.kms.seft203.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class SaveContactRequest extends Contact {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String title;

    private String department;

    private String project;

    private String avatar;

    @NotBlank
    private Integer employeeId;


}
