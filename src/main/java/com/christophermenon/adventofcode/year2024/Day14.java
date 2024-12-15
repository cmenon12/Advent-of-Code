package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day14 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 14);

        // Parse the robots into a 2D arraylist
        ArrayList<ArrayList<Integer>> robots = new ArrayList<>();
        Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        for (String robotText : puzzle) {
            Matcher matcher = pattern.matcher(robotText);
            matcher.find();
            robots.add(new ArrayList<>(Arrays.asList(
                Integer.valueOf(matcher.group(1)),
                Integer.valueOf(matcher.group(2)),
                Integer.valueOf(matcher.group(3)),
                Integer.valueOf(matcher.group(4))
                )));
        }

        // Define the height and width
        int width = 101;
        int height = 103;

        // Move the robots
        // The Math.floorMod function ensures they wrap around the edges correctly
        for (int i = 0; i < 100; i++) {
            for (int r = 0; r < robots.size(); r++) {
                ArrayList<Integer> robot = robots.get(r);
                robot.set(0, Math.floorMod(robot.get(0) + robot.get(2), width));
                robot.set(1, Math.floorMod(robot.get(1) + robot.get(3), height));
            }
        }

        // Calculate the quadrant breakpoints
        int widthBreakpoint = (width - 1)/2;
        int heightBreakpoint = (height - 1)/2;

        // Count the number of robots in each quadrant
        int[] quadrantCounts = new int[]{0, 0, 0, 0};
        for (int r = 0; r < robots.size(); r++) {
            ArrayList<Integer> robot = robots.get(r);
            if (robot.get(0) < widthBreakpoint) {
                if (robot.get(1) < heightBreakpoint) {
                    quadrantCounts[0] += 1;
                } else if (robot.get(1) > heightBreakpoint) {
                    quadrantCounts[1] += 1;
                }
            } else if (robot.get(0) > widthBreakpoint) {
                if (robot.get(1) < heightBreakpoint) {
                    quadrantCounts[2] += 1;
                } else if (robot.get(1) > heightBreakpoint) {
                    quadrantCounts[3] += 1;
                }
            }
        }

        // Multiply the counts
        int total = 1;
        for (int val : quadrantCounts) total *= val;

        System.out.printf("PART 1: %d%n", total);
    }
}
