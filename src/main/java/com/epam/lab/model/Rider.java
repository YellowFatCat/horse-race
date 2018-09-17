package com.epam.lab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rider {

    private String name;
    private String surname;
    private double skill;

    @Override
    public String toString() {
        return name + ' ' + surname + " (skill: " + skill + ")";
    }
}
