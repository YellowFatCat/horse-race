package com.epam.lab.service;

import com.epam.lab.model.Horse;
import com.epam.lab.model.Race;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class EmulationService {

    private final int SCREEN_LENGTH = 60;
    private final int HORSE_LENGTH = 10;

    private final String horse =
            "        ,,_\n" +
                    " _     ~/=-\"\n" +
                    "~ )___~//\n" +
                    " _//---\\|_\n" +
                    "/        /";

    @SneakyThrows
    public Horse startEmulation(Race race) {

        clearConsole();
        TimeUnit.SECONDS.sleep(1);

        RaceSnapshot raceSnapshot = new RaceSnapshot(race);

        while (raceSnapshot.isRaceInAction()) {
            clearConsole();
            printHorses(raceSnapshot);
            TimeUnit.SECONDS.sleep(1);
            raceSnapshot = RaceSnapshot.getSnapshotForCurrentTime(raceSnapshot);
        }

        clearConsole();
        printHorses(raceSnapshot);

        return Collections.max(raceSnapshot.horsesPositions.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    private void printHorses(RaceSnapshot raceSnapshot) {
        Map<Horse, Double> horsesPositions = raceSnapshot.getHorsesPositions();
        for (Map.Entry<Horse, Double> horsePosition : horsesPositions.entrySet()) {
            String name = horsePosition.getKey().getName();

            int gone = toIntExact(Math.round(SCREEN_LENGTH * horsePosition.getValue() / raceSnapshot.getRace().getDistance()));

            int emptySpaceLength = (gone < HORSE_LENGTH) ? gone + 1 : HORSE_LENGTH + 1;
            int distanceBehindHorseLength = (gone > HORSE_LENGTH) ? gone - HORSE_LENGTH : 0;
            int remainingDistanceLength = SCREEN_LENGTH - gone;

            StringBuilder emptySpaceForBottomLine = new StringBuilder();
            for (int i = 0; i < emptySpaceLength; i++) {
                emptySpaceForBottomLine.append(' ');
            }

            StringBuilder emptySpaceForTopLines = new StringBuilder();
            for (int i = 0; i < name.length() + emptySpaceLength + distanceBehindHorseLength; i++) {
                emptySpaceForTopLines.append(' ');
            }

            StringBuilder distanceBehindHorse = new StringBuilder();
            for (int i = 0; i < distanceBehindHorseLength; i++) {
                distanceBehindHorse.append('_');
            }

            StringBuilder remainingDistance = new StringBuilder();
            for (int i = 0; i < remainingDistanceLength; i++) {
                remainingDistance.append('_');
            }

            String[] splittedHorse = horse.split("\\n");
            for (int i = 0; i < splittedHorse.length; i++) {
                StringBuilder line = new StringBuilder();
                if (i == splittedHorse.length - 1) {
                    line.append(name)
                            .append(emptySpaceForBottomLine)
                            .append(distanceBehindHorse)
                            .append(splittedHorse[i])
                            .append(remainingDistance);
                } else {
                    line.append(emptySpaceForTopLines)
                            .append(splittedHorse[i]);
                }
                System.out.println(line);
            }
            System.out.println();
        }
    }

    @Getter
    private static class RaceSnapshot {

        private Race race;
        private Date timestamp;
        private Map<Horse, Double> horsesPositions;

        public RaceSnapshot(Race race) {
            this.race = race;
            this.timestamp = new Date();
            this.horsesPositions = race.getHorses().stream().collect(Collectors.toMap(Function.identity(), (o) -> 0D));
        }

        public RaceSnapshot(RaceSnapshot raceSnapshot) {
            this.race = raceSnapshot.race;
            this.timestamp = new Date();
            this.horsesPositions = raceSnapshot.horsesPositions;
        }

        public static RaceSnapshot getSnapshotForCurrentTime(RaceSnapshot raceSnapshot) {
            RaceSnapshot newRaceSnapshot = new RaceSnapshot(raceSnapshot);
            for (Map.Entry<Horse, Double> entry : newRaceSnapshot.horsesPositions.entrySet()) {
                double newPosition = entry.getValue() + entry.getKey().getMaxSpeed() * entry.getKey().getRider().getSkill() * (newRaceSnapshot.timestamp.getTime() - raceSnapshot.timestamp.getTime()) / 1000;
                entry.setValue(newPosition);
            }
            return newRaceSnapshot;
        }

        @Override
        public String toString() {
            NumberFormat formatter = new DecimalFormat("#0.00");

            return "Time: " + timestamp + "\n" +
                    horsesPositions.entrySet().stream()
                            .map(horsePosition -> horsePosition.getKey().getName() + ": " + formatter.format(horsePosition.getValue()) + " m")
                            .collect(Collectors.joining("\n"));
        }

        private boolean isRaceInAction() {
            return horsesPositions.entrySet().stream()
                    .noneMatch(horsePosition -> horsePosition.getValue() >= race.getDistance());
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
