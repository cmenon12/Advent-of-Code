package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day03 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 3);

        // Complete part 1
        System.out.printf("PART 1: %d%n", getTotal(puzzle));

        // Split the puzzle on don't()
        ArrayList<String> puzzleDont = new ArrayList<>();
        puzzleDont.addAll(Arrays.asList(puzzle.split("don't\\(\\)")));
        
        // Save the total up to the first don't
        int total = getTotal(puzzleDont.get(0));

        // Add the totals after do() after each don't()
        for (String item : puzzleDont.subList(1, puzzleDont.size()-1)) {
            String[] splitItem = item.split("do\\(\\)", 2);
            if (splitItem.length > 1) {
                total += getTotal(splitItem[1]);
            }
            
        }

        // Complete part 2
        System.out.printf("PART 2: %d%n", total);

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
