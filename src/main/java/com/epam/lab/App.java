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

    private String face =
            "       /\\/\\\n" +
            "      /    \\\n" +
            "    ~/(^  ^)\n" +
            "   ~/  )  (\n" +
            "  ~/   (  )\n" +
            " ~/     ~~\n" +
            "~/       | " ;

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml")) {
            App app = context.getBean(App.class);
            app.run();
        }
    }

    public void run() {

        Race race = raceService.getRace(50);

        // Make a bet
        printAllHorses(race.getHorses());
        Horse bet = makeABet(race.getHorses());
        System.out.println("\nYour pick: " + bet.getName() + "\n");

        // Start and finish race
        Horse winner = emulationService.startEmulation(race);

        // Show a winner
        System.out.println("Winner: " + winner.getName());
        System.out.println((winner == bet) ? "You won!" : "Sorry, you lose");

    }

    public Horse makeABet(List<Horse> horses) {

        Scanner scanner = new Scanner(System.in);

        Optional<Horse> bet;
        do {
            System.out.print("\nChoose a horse by name: ");
            String searchQuery = scanner.next();
            bet = horseService.getHorseByName(horses, searchQuery);
            if (!bet.isPresent()) {
                System.out.println("There is no horse with name " + searchQuery + " in current race. Try again.");
            }
        } while (!bet.isPresent());

        return bet.get();
    }

    private void printAllHorses(List<Horse> horses) {
        System.out.println("Race participants: ");
        for (Horse horse : horses) {
            System.out.print("\n" + face);
            System.out.println(horse);
        }
    }
}
