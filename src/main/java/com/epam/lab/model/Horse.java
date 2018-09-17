package com.epam.lab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Horse {

    private String name;
    private Breed breed;
    private Rider rider;
    private int maxSpeed;

    @Override
    public String toString() {
        return name + ":\n breed: " + breed + ",\n rider: " + rider + ",\n maxSpeed: " + maxSpeed + " m/s";
    }
}
