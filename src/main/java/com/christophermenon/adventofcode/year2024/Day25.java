package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day25 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 25)));

        // Create the solution object and solve it
        Solution25 solution = new Solution25(puzzle);
        solution.solveAll();
    }
}

class Solution25 {

    ArrayList<String> puzzle;
    ArrayList<ArrayList<Integer>> keys = new ArrayList<>();
    ArrayList<ArrayList<Integer>> locks = new ArrayList<>();

    Solution25(ArrayList<String> puzzle) {
        this.puzzle = puzzle;
    }

    void solveAll() {

        // Parse all keys and locks
        for (int i = 0; i < puzzle.size(); i += 8) {
            if (".....".equals(puzzle.get(i))) {
                keys.add(parseItem(new ArrayList<>(puzzle.subList(i+1, i+6))));
            } else {
                locks.add(parseItem(new ArrayList<>(puzzle.subList(i+1, i+6))));
            }
        }

        // Try each key and lock
        int total = 0;
        for (int k = 0; k < keys.size(); k++) {
            for (int l = 0; l < locks.size(); l++) {
                if (tryKeyAndLock(keys.get(k), locks.get(l))) total += 1; 
            }
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);
    }

    ArrayList<Integer> parseItem(ArrayList<String> puzzleEntry) {
        ArrayList<Integer> item = new ArrayList<>(Collections.nCopies(5, 0));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (puzzleEntry.get(j).charAt(i) == '#') item.set(i, item.get(i) + 1);
            }
        }
        return item;
    }

    boolean tryKeyAndLock(ArrayList<Integer> key, ArrayList<Integer> lock) {
        // If they overlap in any column then they don't fit, otherwise they fit
        for (int i = 0; i < 5; i++) {
            if (key.get(i) + lock.get(i) > 5) {
                return false;
            }
        }
        return true;
    }
}
