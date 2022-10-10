package com.fu.fuatsbe.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate")
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String name;
    private String phone;
    private String image;
    private String email;
    @Nationalized
    private String address;
    private String status;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<JobApply> jobApplies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Account account;

    @ManyToMany(mappedBy = "candidates")
    @JsonIgnore
    private Collection<Notification> notifications;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<CV> cvs;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<Interview> interviews;

    @ManyToMany
    @JoinTable(name = "cadidate_position", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "cadidate_id"))
    @JsonIgnore
    private Collection<Position> positions;

}
