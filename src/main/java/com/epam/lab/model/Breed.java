package com.epam.lab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Breed {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
