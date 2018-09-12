package com.epam.lab.service;

import com.epam.lab.model.Race;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Setter
public class RaceService {

    private HorseService horseService;

    public Race getRace() {
        return new Race(
                horseService.getSomeHorses(2),
                ThreadLocalRandom.current().nextInt(2500, 4000)
        );
    }
}
