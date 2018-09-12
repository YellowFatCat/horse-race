package com.epam.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
//@Getter
//@Setter
@ToString
public class Race {

    private List<Horse> horses;
    private int distance;
}
