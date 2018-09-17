package com.epam.lab;

import com.epam.lab.service.EmulationService;
import com.epam.lab.service.RaceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private static EmulationService emulationService;
    private static RaceService raceService;

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        emulationService = (EmulationService) context.getBean("emulationService");
        raceService = (RaceService) context.getBean("raceService");

        emulationService.startEmulation(raceService.getRace(50));
    }
}
