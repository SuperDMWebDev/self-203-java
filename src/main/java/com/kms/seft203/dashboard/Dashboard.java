package com.kms.seft203.dashboard;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kms.seft203.user.User;
import com.kms.seft203.widget.Widget;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "dashboard",schema ="public")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @Column(name="title")
    private String title;

    @Column(name="layout_type")
    private String layoutType;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Widget> widgets;

    public Dashboard(String title, String layoutType, User user) {
        this.user= user;
        this.title=title;
        this.layoutType = layoutType;

    }
    public static Dashboard create(SaveDashboardRequest request, User user)
    {
        return new Dashboard(request.getTitle(),request.getLayoutType(),user);
    }
}
