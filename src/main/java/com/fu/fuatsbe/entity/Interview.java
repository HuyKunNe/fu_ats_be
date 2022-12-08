package com.fu.fuatsbe.entity;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@ToString
@Table(name = "interview")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nationalized
    private String subject;
    @Nationalized
    private String purpose;
    private Timestamp date;
    @Nationalized
    private String address;
    private String linkMeeting;
    private String room;
    @Nationalized
    private String round;
    @Column(columnDefinition = "text")
    private String description;
    private String status;
    private String type;
    @Column(name = "candidate_confirm", length = 10)
    private String candidateConfirm;

    @OneToOne(mappedBy = "interview")
    @EqualsAndHashCode.Include
    @ToString.Include
    private InterviewDetail interviewDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "candidateId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "jobApplyId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private JobApply jobApply;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<Notification> notifications;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<InterviewEmployee> interviewEmployees;
}
