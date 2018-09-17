package com.epam.lab.service;

import com.epam.lab.model.Horse;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Setter
public class HorseService {

    private List<Horse> horses;

    public List<Horse> getSomeHorses(int maxCount) {

        return Stream.generate(() -> ThreadLocalRandom.current().nextInt(horses.size()))
                .distinct()
                .limit(maxCount)
                .map(index -> horses.get(index))
                .collect(toList());
    }

    public Optional<Horse> getHorseByName(String name) {
        return horses.stream().filter(horse -> name.equalsIgnoreCase(horse.getName())).findFirst();
    }
}
