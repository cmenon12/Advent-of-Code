package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day19 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 19)));

        // Get a list of towels and patterns
        ArrayList<String> towels = new ArrayList<>();
        towels.addAll(Arrays.asList(puzzle.get(0).split(", ")));
        ArrayList<String> patterns = new ArrayList<>(puzzle.subList(2, puzzle.size()));

        // Try to create each pattern recursively
        int total = 0;
        for (String pattern: patterns) {
            if (createPatternFromTowels(pattern, towels)) total += 1;
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);
    }

    private static boolean createPatternFromTowels(String pattern, ArrayList<String> towels) {
        
        // Break out if we've completed the pattern
        if (pattern.isEmpty()) {
            return true;
        }

        // Try with each towel
        for (String t : towels) {

            // If the towel is not too big
            // And the start of the pattern equals the towel
            // And recrusively following this route completes the pattern
            // Then return true
            if (t.length() <= pattern.length() && 
            t.equals(pattern.substring(0, t.length())) && 
            createPatternFromTowels(pattern.substring(t.length()), towels)) return true;
        }

        // None of the towels could complete the pattern
        return false;
    }
}
