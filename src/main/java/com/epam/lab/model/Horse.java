package com.epam.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Horse {

    private String name;
    private Breed breed;
    private Rider rider;
    private int maxSpeed;

    public void sayHello() {
        System.out.println("Ku!");
    }
}
