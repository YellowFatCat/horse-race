package com.epam.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rider {

    private String name;
    private String surname;
    private double skill;
}
