package com.fu.fuatsbe.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Nationalized;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "interviewDetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date startAt;
    private Date endAt;
    @Nationalized
    private String result;
    private String recordMeeting;
    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interviewId", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Interview interview;
}
