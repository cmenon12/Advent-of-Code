package com.christophermenon.adventofcode.year2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day03 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 3);

        // Complete part 1
        System.out.printf("PART 1: %d%n", getTotal(puzzle));

    }

    private static int getTotal(String text) {
        // Match on mul(dd,dd) with the numbers as groups
        Matcher m = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(text);
        int total = 0;
        while (m.find()) {
            // Increment the total
            total += Integer.valueOf(m.group(1)) * Integer.valueOf(m.group(2));
        }
        return total;
    }
}
