package com.epam.lab.service;

import com.epam.lab.model.Horse;
import com.epam.lab.model.Race;
import lombok.Getter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmulationService {

    public void startEmulation(Race race) throws InterruptedException {

        RaceSnapshot raceSnapshot = new RaceSnapshot(race);

        while (isRaceInAction(raceSnapshot)) {
            System.out.println(raceSnapshot);
            TimeUnit.SECONDS.sleep(1);
            raceSnapshot = RaceSnapshot.getSnapshotForCurrentTime(raceSnapshot);
        }

        System.out.println(raceSnapshot);
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
                            .collect(Collectors.joining("\n"))
                    + "\n";
        }
    }

    private boolean isRaceInAction(RaceSnapshot raceSnapshot) {
        return raceSnapshot.getHorsesPositions().entrySet().stream()
                .noneMatch(horsePosition -> horsePosition.getValue() >= raceSnapshot.getRace().getDistance());
    }
}
