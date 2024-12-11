package com.christophermenon.adventofcode.year2024;

import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day07 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 7);

        long total = 0;
        for (String row : puzzle) {
            // Get the total for the row and an array of numbers
            long targetRowTotal = Long.parseLong(row.split(": ")[0]);
            int[] rowNumbers = Arrays.stream(row.split(": ")[1].split(" ")).mapToInt(Integer::parseInt).toArray();

            // We need to pad the binary string of operator combinations with 0s
            int binaryStringLength = Integer.toBinaryString((int)Math.round(Math.pow(2, (rowNumbers.length - 1)))-1).length();
            String binaryFormatPattern = "%" + binaryStringLength + "s";

            // Iterate over all possible binary combinations for the operators
            for (int i = 0; i < Math.pow(2, (rowNumbers.length - 1)); i++) {
                long rowTotal = rowNumbers[0];

                // Get the list of operators, representing + and * as 0 and 1
                String operators = String.format(binaryFormatPattern, Integer.toBinaryString(i)).replace(' ', '0');

                // Apply each operator
                for (int j = 0; j < operators.length(); j++) {
                    if (operators.charAt(j) == '0') {
                        rowTotal += rowNumbers[j + 1];
                    } else {
                        rowTotal *= rowNumbers[j + 1];
                    }
                }

                // Stop trying new combinations if this one is correct
                if (rowTotal == targetRowTotal) {
                    total += targetRowTotal;
                    break;
                }
            }
        }
        
        // Complete part 1
        System.out.printf("PART 1: %d%n", total);
    }
}
