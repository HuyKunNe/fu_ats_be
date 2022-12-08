package com.fu.fuatsbe.entity;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String subject;
    @Nationalized
    @Column(columnDefinition="TEXT")
    private String content;
    private Date createTime;
    private String status;


    @ManyToMany
    @JoinTable(name = "notified_candidate", joinColumns = @JoinColumn(name = "notifice_id"), inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    @JsonIgnore
    private Collection<Candidate> candidates;

    @ManyToMany
    @JoinTable(name = "notified_employee", joinColumns = @JoinColumn(name = "notifice_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @JsonIgnore
    private Collection<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "interviewId")
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Interview interview;
}
