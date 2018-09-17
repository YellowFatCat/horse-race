package com.epam.lab;

import com.epam.lab.model.Horse;
import com.epam.lab.model.Race;
import com.epam.lab.service.EmulationService;
import com.epam.lab.service.HorseService;
import com.epam.lab.service.RaceService;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Setter
public class App {

    private EmulationService emulationService;
    private RaceService raceService;
    private HorseService horseService;

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml")) {
            App app = new App();
            app.run(context);
        }
    }

    public void run(ConfigurableApplicationContext context) {

        emulationService = context.getBean(EmulationService.class);
        raceService = context.getBean(RaceService.class);
        horseService = context.getBean(HorseService.class);


        Race race = raceService.getRace(50);

        Horse bet = makeABet(race.getHorses());
        System.out.println("\nYour pick: " + bet.getName() + "\n");

        Horse winner = emulationService.startEmulation(race);

        System.out.println("\nWinner: " + winner.getName());
        System.out.println((winner == bet) ? "You won!" : "Sorry, you lose");

    }

    public Horse makeABet(List<Horse> horses) {
        System.out.println("Race participants: ");
        for (Horse horse : horses) {
            System.out.println(horse);
        }

        Scanner scanner = new Scanner(System.in);

        Optional<Horse> bet;
        do {
            System.out.print("\nChoose a horse by name: ");
            String searchQuery = scanner.next();
            bet = horseService.getHorseByName(searchQuery);
            if (!bet.isPresent()) {
                System.out.println("There is no horse with name " + searchQuery + " in current race. Try again.");
            }
        } while (!bet.isPresent());

        return bet.get();
    }
}
