package com.epam.lab;

import com.epam.lab.model.Horse;
import com.epam.lab.model.Race;
import com.epam.lab.service.EmulationService;
import com.epam.lab.service.HorseService;
import com.epam.lab.service.RaceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static EmulationService emulationService;
    private static RaceService raceService;
    private static HorseService horseService;

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        emulationService = (EmulationService) context.getBean("emulationService");
        raceService = (RaceService) context.getBean("raceService");
        horseService = (HorseService) context.getBean("horseService");

        Race race = raceService.getRace(50);

        Horse bet = makeABet(race.getHorses());
        System.out.println("\nYour pick: " + bet.getName() + "\n");

        Horse winner = emulationService.startEmulation(race);
        System.out.println("\nWinner: " + winner.getName());

        System.out.println((winner == bet) ? "You won!" : "Sorry, you lose");
    }

    public static Horse makeABet(List<Horse> horses) {
        System.out.println("Race participants: ");
        for (Horse horse : horses) {
            System.out.println(horse);
        }

        Optional<Horse> bet;
        do {
            System.out.print("\nChoose a horse by name: ");
            Scanner scanner = new Scanner(System.in);
            String searchQuery = scanner.next();
            bet = horseService.getHorseByName(searchQuery);
            if (!bet.isPresent()) {
                System.out.println("There is no horse with name " + searchQuery + " in current race. Try again.");
            }
        } while (!bet.isPresent());

        return bet.get();
    }
}
