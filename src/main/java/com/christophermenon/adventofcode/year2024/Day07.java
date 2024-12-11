package com.christophermenon.adventofcode.year2024;

import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day07 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 7);

        long part1Total = 0;
        long part2Total = 0;
        for (String row : puzzle) {
            // Get the total for the row and an array of numbers
            long targetRowTotal = Long.parseLong(row.split(": ")[0]);
            int[] rowNumbers = Arrays.stream(row.split(": ")[1].split(" ")).mapToInt(Integer::parseInt).toArray();

            // Pad the binary strings of operator combinations with 0s
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
                    part1Total += targetRowTotal;
                    break;
                }
            }

            // Pad the tenary strings of operator combinations with 0s
            int tenaryStringLength = Integer.toString((int)Math.round(Math.pow(3, (rowNumbers.length - 1)))-1, 3).length();
            String tenaryFormatPattern = "%" + tenaryStringLength + "s";

            // Iterate over all possible tenary combinations for the operators
            for (int i = 0; i < Math.pow(3, (rowNumbers.length - 1)); i++) {
                long rowTotal = rowNumbers[0];

                // Get the list of operators, representing +, *, and || as 0, 1, and 2
                String operators = String.format(tenaryFormatPattern, Integer.toString(i, 3)).replace(' ', '0');

                // Apply each operator
                for (int j = 0; j < operators.length(); j++) {
                    if (operators.charAt(j) == '0') {
                        rowTotal += rowNumbers[j + 1];
                    } else if (operators.charAt(j) == '1') {
                        rowTotal *= rowNumbers[j + 1];
                    } else {
                        rowTotal = Long.parseLong(rowTotal + "" + rowNumbers[j + 1]);
                    }
                }

                // Stop trying new combinations if this one is correct
                if (rowTotal == targetRowTotal) {
                    part2Total += targetRowTotal;
                    break;
                }
            }
        }
        
        // Complete both parts
        System.out.printf("PART 1: %d%n", part1Total);
        System.out.printf("PART 2: %d%n", part2Total);
    }
}
