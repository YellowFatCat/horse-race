package com.epam.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class Race {

    private List<Horse> horses;
    private int distance;
}
