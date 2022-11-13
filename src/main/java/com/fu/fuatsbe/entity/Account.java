package com.fu.fuatsbe.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@ToString
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    private String password;
    private String email;
    private String status;
    @Column(name = "notification_token")
    private String notificationToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "roleId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Role role;

    @JsonBackReference
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee employee;

    @JsonBackReference
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Candidate candidate;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private VerificationToken verificationToken;
}
