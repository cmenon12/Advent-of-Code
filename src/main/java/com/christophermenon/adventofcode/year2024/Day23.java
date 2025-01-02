package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day23 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 23)));

        // Create the solution object and solve it
        Solution23 solution = new Solution23(puzzle);
        solution.solveAll();
    }
}

class Solution23 {

    ArrayList<String[]> pairs = new ArrayList<>();
    ArrayList<String[]> setsOfThree = new ArrayList<>();

    Solution23(ArrayList<String> puzzle) {
        for (String row : puzzle) pairs.add(row.split("-"));
    }

    void solveAll() {

        for (String[] pair : pairs) {

            // Find all pairings for each computer in the pair
            ArrayList<String> allPairings0 = findAllPairings(pair[0]);
            ArrayList<String> allPairings1 = findAllPairings(pair[1]);

            // Iterate through them all
            for (String val0 : allPairings0) {
                if (!val0.equals(pair[1])) {
                    for (String val1 : allPairings1) {

                        // If both computers have the same pair separately, and this doesn't exist yet
                        // Then save it
                        if (!val1.equals(pair[0]) && val0.equals(val1) && !checkForSetOfThree(pair[0], pair[1], val1)) {
                            setsOfThree.add(new String[] {pair[0], pair[1], val1});
                        }
                    }
                    
                }
            }

        }

        // Count how many start with t
        int total = 0;
        for (String[] set : setsOfThree) {
            if (setStartsWithLetterT(set)) total += 1;
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);
    }

    ArrayList<String> findAllPairings(String computer) {
        ArrayList<String> result = new ArrayList<>();
        for (String[] pair : pairs) {
            if (pair[0].equals(computer)) result.add(pair[1]);
            else if (pair[1].equals(computer)) result.add(pair[0]);   
        }
        return result;
    }

    boolean checkForSetOfThree(String c1, String c2, String c3) {
        for (String[] set : setsOfThree) {
            if (Arrays.stream(set).anyMatch(c1::equals) && 
                Arrays.stream(set).anyMatch(c2::equals) && 
                Arrays.stream(set).anyMatch(c3::equals)) return true;
        }
        return false;
    }

    boolean setStartsWithLetterT(String[] set) {
        for (String val : set) {
            if (val.charAt(0) == 't') return true;
        }
        return false;
    }
}
