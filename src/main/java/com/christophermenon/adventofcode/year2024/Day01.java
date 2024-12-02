package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Collections;

import com.christophermenon.adventofcode.PuzzleInput;

/**
 *
 * @author cmenon12
 */
public class Day01 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 1);

        // Put the numbers in two columns
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        for (String row : puzzle) {
            left.add(Integer.parseInt(row.substring(0, 5)));
            right.add(Integer.parseInt(row.substring(8, 13)));
        }

        // Sort in ascending order
        Collections.sort(left);
        Collections.sort(right);

        // Sum the differences
        int total = 0;
        for (int i = 0; i < left.size(); i++) {
            total += Math.abs(left.get(i) - right.get(i));
        }

        System.out.printf("PART 1: %d\n", total);
    }
}
