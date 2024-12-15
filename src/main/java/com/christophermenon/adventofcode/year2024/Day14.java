package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day14 {

    public static void main(String[] args) throws InterruptedException {
        String[] puzzle = PuzzleInput.getAsArray(2024, 14);

        // Define the height and width
        int width = 101;
        int height = 103;

        // Parse the robots into a 2D arraylist
        ArrayList<ArrayList<Integer>> robots = prepareRobots(puzzle);

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

        // Recreate the robots
        robots = prepareRobots(puzzle);

        // Move the robots
        // The Math.floorMod function ensures they wrap around the edges correctly
        for (int i = 0; i < 10000; i++) {
            for (int r = 0; r < robots.size(); r++) {
                ArrayList<Integer> robot = robots.get(r);
                robot.set(0, Math.floorMod(robot.get(0) + robot.get(2), width));
                robot.set(1, Math.floorMod(robot.get(1) + robot.get(3), height));
            }

            // Print the map for manual verification if it's possibly here
            if (guessTree(robots, width, height) && i >= 0) {
                System.out.printf("This is after %d seconds%n", i + 1);
                printMap(robots, width, height);
            }
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);
    }

    private static ArrayList<ArrayList<Integer>> prepareRobots(String[] puzzle) {
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
        return robots;
    }

    private static boolean guessTree(ArrayList<ArrayList<Integer>> robots, int width, int height) {

        // Count the number of robots in each column
        Integer[] columnCount = new Integer[width];
        Integer[] rowCount = new Integer[height];
        Arrays.fill(columnCount, 0);
        Arrays.fill(rowCount, 0);
        for (ArrayList<Integer> robot : robots) {
            columnCount[robot.get(0)] += 1;
            rowCount[robot.get(1)] += 1;
        }

        // Get the largest numbers at the start
        Arrays.sort(columnCount, Collections.reverseOrder());
        Arrays.sort(rowCount, Collections.reverseOrder());

        // Return true if there are 5 columns with >=20 robots,
        // and two rows with >= 30 robots
        return columnCount[4] >= 20 && rowCount[1] >= 30;
    }

    private static void printMap(ArrayList<ArrayList<Integer>> robots, int width, int height) {
        
        // For each row
        for (int h = 0; h < height; h++) {

            // For each column in the row
            StringBuilder row = new StringBuilder(width);
            for (int w = 0; w < width; w++) {

                // Add a X if the robot exists, otherwise .
                boolean hasRobot = false;
                for (ArrayList<Integer> robot : robots) {
                    if (robot.get(0) == w && robot.get(1) == h) {
                        row.append('X');
                        hasRobot = true;
                        break;
                    }
                }
                if (!hasRobot) row.append(' ');
            }
            System.out.println(row.toString());
        }
    }
}
