package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day08 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 8)));

        // Create empty maps of antinodes
        int[][] part1Antinodes = new int[puzzle.size()][puzzle.get(0).length()];
        int[][] part2Antinodes = new int[puzzle.size()][puzzle.get(0).length()];

        // Will refer to these as letters to avoid confusion with chars
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (char letterChar : letters.toCharArray()) {

            // Get all the locations of letters, and skip this one if there's none
            String letter = String.valueOf(letterChar);
            int[][] locations = getAllLocationsOfLetter(puzzle, letter);
            if (locations.length == 0) continue;


            // Pair up each location, but skip equal pairings
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations.length; j++) {
                    if (i == j) continue;

                    // Get first and second antinodes for part 1
                    int[] antinode = new int[]{ (locations[i][0] * 2) - locations[j][0],  (locations[i][1] * 2) - locations[j][1]};
                    if (isValidAntinode(puzzle, antinode)) part1Antinodes[antinode[0]][antinode[1]] = 1;
                    antinode = new int[]{ (locations[j][0] * 2) - locations[i][0],  (locations[j][1] * 2) - locations[i][1]};
                    if (isValidAntinode(puzzle, antinode)) part1Antinodes[antinode[0]][antinode[1]] = 1;

                    // Get first antinode for part 2
                    int[] diff = new int[]{locations[i][0] - locations[j][0], locations[i][1] - locations[j][1]};
                    antinode = new int[]{locations[i][0] + diff[0], locations[i][1] + diff[1]};
                    if (isValidAntinode(puzzle, antinode)) {
                        while (isValidAntinode(puzzle, antinode)) {
                            part2Antinodes[antinode[0]][antinode[1]] = 1;
                            antinode = new int[]{antinode[0] + diff[0], antinode[1] + diff[1]};
                        }
                    }

                    // Get second antinode for part 2
                    diff = new int[]{locations[j][0] - locations[i][0], locations[j][1] - locations[i][1]};
                    antinode = new int[]{locations[j][0] - diff[0], locations[j][1] - diff[1]};
                    if (isValidAntinode(puzzle, antinode)) {
                        while (isValidAntinode(puzzle, antinode)) {
                            part2Antinodes[antinode[0]][antinode[1]] = 1;
                            antinode = new int[]{antinode[0] - diff[0], antinode[1] - diff[1]};
                        }
                    }
                }
            }
        }

        System.out.printf("PART 1: %d%n", Arrays.stream(part1Antinodes).flatMapToInt(Arrays::stream).sum());
        System.out.printf("PART 2: %d%n", Arrays.stream(part2Antinodes).flatMapToInt(Arrays::stream).sum());
    }

    private static int[][] getAllLocationsOfLetter(ArrayList<String> puzzle, String letter) {
        ArrayList<int[]> locations = new ArrayList<>();

        // Get all locations of the letter on each row
        for (int i = 0; i < puzzle.size(); i++) {
            int j = puzzle.get(i).indexOf(letter);
            while (j != -1) {
                locations.add(new int[]{i, j});
                j = puzzle.get(i).indexOf(letter, j + 1);
            }
        }

        // Convert to int[][]
        int[][] result = new int[locations.size()][2];
        for (int i = 0; i < result.length; i++) result[i] = locations.get(i);
        return result;
    }

    private static boolean isValidAntinode(ArrayList<String> puzzle, int[] antinode) {
        return antinode[0] >= 0 && antinode[0] < puzzle.size() && antinode[1] >= 0 && antinode[1] < puzzle.get(0).length();
    }
}
