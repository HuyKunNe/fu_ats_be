package com.fu.fuatsbe.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "email_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String title;
    @Column(columnDefinition="TEXT")
    private String content;
}
