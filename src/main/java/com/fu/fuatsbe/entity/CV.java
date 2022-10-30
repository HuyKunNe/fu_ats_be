package com.fu.fuatsbe.entity;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cv")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String linkCV;
    private String result;
    @Nationalized
    private String suitablePosition;
    @Column(columnDefinition = "text")
    private String note;
    @Nationalized
    private String experience;
    @Nationalized
    private String location;
    private String status;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonIgnore
    private Collection<JobApply> jobApplies;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "candidateId")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Candidate candidate;

    @ManyToMany
    @JoinTable(name = "cv_position", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "cv_id"))
    private Collection<Position> positions;

}
