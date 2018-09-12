package com.epam.lab.service;

import com.epam.lab.model.Horse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HorseService {

    public List<Horse> getAllHorses() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            horses.add((Horse) context.getBean("horse" + i));
        }
        return horses;
    }

    public List<Horse> getSomeHorses(int maxCount) {
        List<Horse> horses = getAllHorses();
//        return horses.stream().limit(maxCount).collect(toSet());

        while (horses.size() > maxCount) {
            horses.remove(ThreadLocalRandom.current().nextInt(horses.size()));
        }

        return horses;
    }
}
