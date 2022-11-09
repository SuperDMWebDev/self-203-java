package com.kms.seft203.dashboard;

import com.kms.seft203.widget.Widget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
public class SaveDashboardRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String layoutType;

    private Set<Widget> widgets;
}
