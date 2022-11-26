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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "planDetail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amount;
    private Date date;
    private String status;
    @Column(columnDefinition = "text")
    private String reason;
    private String salary;
    private String name;
    private Date periodFrom;
    private Date periodTo;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String requirement;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "recruitmentPlanId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private RecruitmentPlan recruitmentPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "ceoId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee ceo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "approverId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Employee approver;

    @OneToMany(mappedBy = "planDetail", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<RecruitmentRequest> recruitmentRequests;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "positionId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Position position;
}
