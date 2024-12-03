package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Collections;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day01 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 1);

        // Put the numbers in two columns
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        for (String row : puzzle) {
            left.add(Integer.valueOf(row.substring(0, 5)));
            right.add(Integer.valueOf(row.substring(8, 13)));
        }

        // Sort in ascending order
        Collections.sort(left);
        Collections.sort(right);

        // Sum the differences
        int total = 0;
        for (int i = 0; i < left.size(); i++) {
            total += Math.abs(left.get(i) - right.get(i));
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);

        // Put the numbers in two columns
        left = new ArrayList<>();
        right = new ArrayList<>();
        for (String row : puzzle) {
            left.add(Integer.valueOf(row.substring(0, 5)));
            right.add(Integer.valueOf(row.substring(8, 13)));
        }

        // Sum the similarity
        int similarity = 0;
        for (Integer leftNum : left) {
            similarity += leftNum * Collections.frequency(right, leftNum);
        }

        // Complete part 2
        System.out.printf("PART 2: %d%n", similarity);
    }
}
