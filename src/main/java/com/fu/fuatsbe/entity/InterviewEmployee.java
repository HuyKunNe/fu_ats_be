package com.fu.fuatsbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "interview_employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEmployee {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Employee employee;
}
