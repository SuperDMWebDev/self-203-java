package com.kms.seft203.widget;

import com.kms.seft203.config.Config;
import com.kms.seft203.dashboard.Dashboard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="widget",schema="public")
@Getter
@Setter

public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String title;

    @Column(name="widget_type",length = 20)
    private String widgetType;

    @Column(name="min_width")
    private Integer minWidth;

    @Column(name="min_height")
    private Integer minHeight;

    @ManyToOne
    @JoinColumn(name="dashboard_id")
    private Dashboard dashboard;


}
