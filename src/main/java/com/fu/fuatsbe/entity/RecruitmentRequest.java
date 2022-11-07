package com.fu.fuatsbe.entity;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recruitmentRequest")
@Builder
public class RecruitmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;
    private Date expiryDate;
    @Nationalized
    private String industry;
    private int amount;
    private String jobLevel;
    @Nationalized
    private String experience;
    private String typeOfWork;
    private String salaryFrom;
    private String salaryTo;
    @Nationalized
    private String educationLevel;
    @Nationalized
    private String foreignLanguage;
    @Nationalized
    private String address;

    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String requirement;
    @Column(columnDefinition = "text")
    private String benefit;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "planDetailId")
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private PlanDetail planDetail;

    @OneToMany(mappedBy = "recruitmentRequest", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<JobApply> jobApplies;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "positionId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Position position;

    @ManyToMany
    @JoinTable(name = "recruitmentRquest_city", joinColumns = @JoinColumn(name = "request_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
    @JsonIgnore
    private Collection<City> cities;

}
