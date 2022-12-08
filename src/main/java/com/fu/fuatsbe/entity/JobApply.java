package com.fu.fuatsbe.entity;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "jobApply")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;
    private String status;
    @Nationalized
    private String educationLevel;
    @Nationalized
    private String foreignLanguage;
    @Nationalized
    private String experience;

    private String screeningStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "recruitmentRequestId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private RecruitmentRequest recruitmentRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "applierId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee applier;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "candidateId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cvId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private CV cv;

    @OneToMany(mappedBy = "jobApply", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<Interview> interviews;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cityIdMany")
    @EqualsAndHashCode.Include
    @ToString.Include
    private City cities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private City city;
}
