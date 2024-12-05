package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day04 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>();
        puzzle.addAll(Arrays.asList(PuzzleInput.getAsArray(2024, 4)));

        // Add ... at the start and end of each row
        for (int i = 0; i < puzzle.size(); i++) {
            puzzle.set(i, "..." + puzzle.get(i) + "...");
        }

        // Add 3 rows of ... at the top and bottom of the puzzle
        puzzle.addAll(0, Collections.nCopies(3, ".".repeat(puzzle.get(0).length())));
        puzzle.addAll(Collections.nCopies(3, ".".repeat(puzzle.get(0).length())));

        int part1total = 0;
        int part2total = 0;
        // Traverse through grid and get each X
        for (int y = 3; y < puzzle.size() - 3; y++) {
            String row = puzzle.get(y);
            for (int x = 3; x < row.length() - 3; x++) {
                if (row.charAt(x) == 'X') {

                    // Extract a 7x7 grid around this X
                    // Make a copy of the ArrayList
                    ArrayList<String> sevenRows = new ArrayList<>();
                    for (int i = y-3; i < y+4; i++) {
                        sevenRows.add(puzzle.get(i));
                    }
                    for (int i = 0; i < sevenRows.size(); i++) {
                        sevenRows.set(i, sevenRows.get(i).substring(x - 3, x + 4));
                    }

                    // Count XMAS in this grid 
                    part1total += countXmas(sevenRows);
                }

                if (row.charAt(x) == 'A') {

                    // Extract a 3x3 grid around this A
                    // Make a copy of the ArrayList
                    ArrayList<String> threeRows = new ArrayList<>();
                    for (int i = y-1; i < y+2; i++) {
                        threeRows.add(puzzle.get(i));
                    }
                    for (int i = 0; i < threeRows.size(); i++) {
                        threeRows.set(i, threeRows.get(i).substring(x - 1, x + 2));
                    }

                    // Check diagonal MAS in this grid 
                    part2total += checkDiagonalMas(threeRows);
                }
            }
        }

        // Complete both parts
        System.out.printf("PART 1: %d%n", part1total);
        System.out.printf("PART 2: %d%n", part2total);

    }

    private static int countXmas(ArrayList<String> sevenRows) {
        int total = 0;

        // Find horizontal XMAS
        if ("SAMX".equals(sevenRows.get(3).substring(0, 4))) total += 1;
        if ("XMAS".equals(sevenRows.get(3).substring(3, 7))) total += 1;

        // Find vertical upwards XMAS
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            text.append(sevenRows.get(i).charAt(3));
        }
        if ("SAMX".equals(text.toString())) total += 1;

        // Find vertical downwards XMAS
        text = new StringBuilder();
        for (int i = 3; i < 7; i++) {
            text.append(sevenRows.get(i).charAt(3));
        }
        if ("XMAS".equals(text.toString())) total += 1;
    
        // Find top-left diagonal XMAS
        text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            text.append(sevenRows.get(i).charAt(i));
        }
        if ("SAMX".equals(text.toString())) total += 1;

        // Find bottom-right diagonal XMAS
        text = new StringBuilder();
        for (int i = 3; i < 7; i++) {
            text.append(sevenRows.get(i).charAt(i));
        }
        if ("XMAS".equals(text.toString())) total += 1;

        // Find botttom-left diagonal XMAS
        text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            text.append(sevenRows.get(6 - i).charAt(i));
        }
        if ("SAMX".equals(text.toString())) total += 1;

        // Find top-right diagonal XMAS
        text = new StringBuilder();
        for (int i = 3; i < 7; i++) {
            text.append(sevenRows.get(6 - i).charAt(i));
        }
        if ("XMAS".equals(text.toString())) total += 1;

        return total;
    }

    private static int checkDiagonalMas(ArrayList<String> threeRows) {
        int total = 0;

        // Find top-left to bottom-right MAS
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            text.append(threeRows.get(i).charAt(i));
        }
        if ("MAS".equals(text.toString()) || "SAM".equals(text.toString())) total += 1;

        // Find bottom-left to top-right SAM
        text = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            text.append(threeRows.get(2 - i).charAt(i));
        }
        if ("MAS".equals(text.toString()) || "SAM".equals(text.toString())) total += 1;

        return total == 2 ? 1 : 0;
    }
}
