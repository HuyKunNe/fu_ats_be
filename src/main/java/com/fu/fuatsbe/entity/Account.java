package com.fu.fuatsbe.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
