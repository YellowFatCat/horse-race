package com.epam.lab;

import com.epam.lab.service.RaceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private static RaceService raceService;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        raceService = (RaceService) context.getBean("raceService");
        System.out.println(raceService.getRace());
    }
}
