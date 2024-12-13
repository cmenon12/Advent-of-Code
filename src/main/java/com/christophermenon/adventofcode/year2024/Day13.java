package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day13 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 13)));

        int totalCost = 0;
        for (int i = 0; i < puzzle.size(); i += 4) {

            // Prepare arcade data
            ArrayList<int[]> arcade = parseArcade(puzzle.subList(i, i + 3));
            int[] buttonA = arcade.get(0);
            int[] buttonB = arcade.get(1);
            int[] prize = arcade.get(2);

            // Iterate over all button combinations
            int smallestCost = -1;
            for (int a = 0; a <= 100; a++) {
                for (int b = 0; b <= 100; b++) {

                    // Check if the prize is reached and save the smallest cost
                    int xMove = (buttonA[0] * a) + (buttonB[0] * b);
                    int yMove = (buttonA[1] * a) + (buttonB[1] * b);
                    if (xMove == prize[0] && yMove == prize[1]) {
                        int cost = (a * 3) + b;
                        if (smallestCost == -1 || cost < smallestCost) smallestCost = cost;
                    }
                }
            }
            if (smallestCost != -1) totalCost += smallestCost;
        }
        System.out.printf("PART 1: %d%n", totalCost);
    }

    private static ArrayList<int[]> parseArcade(List<String> puzzle) {
        ArrayList<int[]> result = new ArrayList<>();

        // Extract the two buttons
        Pattern pattern = Pattern.compile("X\\+(\\d+), Y\\+(\\d+)");
        Matcher matcher = pattern.matcher(puzzle.get(0).split(" ", 3)[2]);
        matcher.find();
        result.add(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))});
        matcher = pattern.matcher(puzzle.get(1).split(" ", 3)[2]);
        matcher.find();
        result.add(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))});

        // Extract the prize
        pattern = Pattern.compile("X\\=(\\d+), Y\\=(\\d+)");
        matcher = pattern.matcher(puzzle.get(2).split(" ", 2)[1]);
        matcher.find();
        result.add(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))});

        return result;
    }
}
