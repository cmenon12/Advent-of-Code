package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day10 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 10)));

        // Iterate through 0 on the map
        int part1Total = 0;
        int part2Total = 0;
        for (int i = 0; i < puzzle.size(); i++) {
            int j = puzzle.get(i).indexOf('0');
            while (j != -1) {
                int[] currentPosition = new int[]{i, j};

                // Get the 9s reachable from the 0
                ArrayList<int[]> trailEnds = followTrailhead(puzzle, currentPosition);
                part2Total += trailEnds.size();

                // Remove duplicate 9s for part 1
                ArrayList<int[]> validTrailEnds = new ArrayList<>();
                for (int[] newItem : trailEnds) {
                    boolean alreadyExists = false;
                    for (int[] existingItem : validTrailEnds) {
                        if (Arrays.equals(newItem, existingItem)) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) validTrailEnds.add(newItem);
                }

                // Save them and iterate
                part1Total += validTrailEnds.size();
                j = puzzle.get(i).indexOf('0', j + 1);
            }
        }

        System.out.printf("PART 1: %d%n", part1Total);
        System.out.printf("PART 2: %d%n", part2Total);
    }

    private static ArrayList<int[]> followTrailhead(ArrayList<String> puzzle, int[] currentPosition) {
        int currentValue = puzzle.get(currentPosition[0]).charAt(currentPosition[1]) - '0';
        ArrayList<int[]> nextLocations = findNextStepLocations(puzzle, currentPosition);

        // Break out of the loop, we have the successful trails
        if (currentValue == 8) return nextLocations;

        // Call this function recursively for each next location
        ArrayList<int[]> total = new ArrayList<>();
        for (int[] location : nextLocations) {
            total.addAll(followTrailhead(puzzle, location));
        }
        return total;

    }

    private static ArrayList<int[]> findNextStepLocations(ArrayList<String> puzzle, int[] currentPosition) {
        ArrayList<int[]> locations = new ArrayList<>();
        int currentValue = puzzle.get(currentPosition[0]).charAt(currentPosition[1]) - '0';

        // If the current value is valid
        if (currentValue >= 0 && currentValue <= 8) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int[] newPosition = new int[]{currentPosition[0] + i, currentPosition[1] + j};

                    // Don't check the current value or diagonals
                    // Check the new position is valid
                    if ((i == 0 && j == 0) || (i != 0 && j != 0) || 
                    newPosition[0] < 0 || newPosition[0] >= puzzle.size() || 
                    newPosition[1] < 0 || newPosition[1] >= puzzle.get(0).length()) continue;

                    // Save the new location
                    if (puzzle.get(newPosition[0]).charAt(newPosition[1]) - '0' == currentValue + 1) {
                        locations.add(newPosition);
                    }
                }
            }
        }
        return locations;     
    }
}
