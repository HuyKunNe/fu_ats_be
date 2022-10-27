package com.fu.fuatsbe.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String name;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Collection<CV> cvs;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Collection<PlanDetail> planDetails;

    public Skill(String name) {
        this.name = name;
    }

}
