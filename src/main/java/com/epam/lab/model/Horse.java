package com.epam.lab.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Horse {

    private String name;
    private Breed breed;
    private Rider rider;
    private int maxSpeed;
}
