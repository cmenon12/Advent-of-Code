package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day19 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 19)));

        // Get a list of towels and patterns
        ArrayList<String> towels = new ArrayList<>();
        towels.addAll(Arrays.asList(puzzle.get(0).split(", ")));
        ArrayList<String> patterns = new ArrayList<>(puzzle.subList(2, puzzle.size()));

        // Create a solution object and solve it
        Solution solution = new Solution(towels);
        solution.solveAll(patterns);
    }
}

class Solution {

    ArrayList<String> towels;
    HashMap<String, Long> preSolvedPatterns;

    // Create the variables we want across the solution methods
    Solution(ArrayList<String> towels) {
        this.towels = towels;
        this.preSolvedPatterns = new HashMap<>();
    }

    void solveAll(ArrayList<String> patterns) {
        long part1Total = 0;
        long part2Total = 0;

        // Solve each pattern
        for (String pattern: patterns) {
            long total = createPatternFromTowels(pattern);
            if (total > 0) part1Total += 1;
            part2Total += total;
        }

        // Complete both parts
        System.out.printf("PART 1: %d%n", part1Total);
        System.out.printf("PART 2: %d%n", part2Total);
    }

    long createPatternFromTowels(String pattern) {
        
        // Break out if we've completed the pattern
        if (pattern.isEmpty()) {
            return 1;
        }

        // Try with each towel
        long allTotals = 0;
        for (String t : towels) {

            // If the towel is not too big
            // And the start of the pattern equals the towel
            if (t.length() <= pattern.length() && 
            t.equals(pattern.substring(0, t.length()))) {

                // If we don't already have the total for the next pattern
                // Then recursively solve it and save it
                if (!preSolvedPatterns.containsKey(pattern.substring(t.length()))) {
                    preSolvedPatterns.put(pattern.substring(t.length()), createPatternFromTowels(pattern.substring(t.length())));
                }

                // Add the total for the next pattern
                allTotals += preSolvedPatterns.get(pattern.substring(t.length()));
            }
        }

        // Return the total number of solutions
        return allTotals;
    }
}
