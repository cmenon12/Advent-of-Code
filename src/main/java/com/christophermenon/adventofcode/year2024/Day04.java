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

        int total = 0;
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

                    // Count the XMAS in this grid 
                    total += countXmas(sevenRows);
                }
            }
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);

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
}
