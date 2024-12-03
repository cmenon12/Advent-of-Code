package com.christophermenon.adventofcode.year2024;

import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day02 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 2);

        // Iterate over each row
        int totalSafe = 0;
        for (String row : puzzle) {
            int[] rowNumbers = Arrays.stream(row.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

            // Store the direction of numbers in a row and whether it's safe
            int rowDirection = 0;
            boolean rowSafe = true;
            for (int i = 1; i < rowNumbers.length; i++) {
                int diff = rowNumbers[i] - rowNumbers[i-1];

                // Unsafe if diff is not -3, -2, -1, 1, 2, 3
                // Unsafe if direction is set and diff is in wrong direction
                if (diff == 0 || diff < -3 || diff > 3 || (rowDirection < 0 && diff > -1) || (rowDirection > 0 && diff < 1)) {
                    rowSafe = false;
                    break;

                // If we haven't set the direction yet, then set it
                } else if (rowDirection == 0) {
                    rowDirection = diff;
                }
            }

            if (rowSafe) {
                totalSafe += 1;
            }
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", totalSafe);
    }
}
