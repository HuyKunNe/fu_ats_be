package com.fu.fuatsbe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "cvScreening")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int city;
    private int experience;
    private int educationLevel;
    private int foreignLanguage;
    private int percentRequired;

}
