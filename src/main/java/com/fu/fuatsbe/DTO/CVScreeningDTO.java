package com.fu.fuatsbe.DTO;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CVScreeningDTO {

    private int city;
    private int experience;
    private int educationLevel;
    private int foreignLanguage;
    private int percentRequired;

}
