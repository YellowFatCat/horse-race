package com.epam.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Race {

    private List<Horse> horses;
    private int distance;
}
