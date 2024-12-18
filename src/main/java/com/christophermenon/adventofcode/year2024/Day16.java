package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day16 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 16)));
        Direction direction = Direction.RIGHT;

        Long total = followMaze(puzzle, direction, 0L);

        System.out.printf("PART 1: %d%n", total);
    }

    private static Long followMaze(ArrayList<String> puzzle, Direction currentDirection, Long currentTotal) {
        int[] currentPosition = getCoordinates(puzzle);

        // Break out if at the end
        if (currentPosition[0] == 1 && currentPosition[1] == puzzle.get(1).length() - 2) {
            return currentTotal;
        }

        // Create list for possible route totals
        // Use a suitably large number as an edge case
        ArrayList<Long> possibleTotals = new ArrayList<>();
        possibleTotals.add(Long.MAX_VALUE);

        // Try continuing in current direction
        if (checkIfCanMove(puzzle, currentDirection)) {
            possibleTotals.add(followMaze(move(puzzle, currentDirection), currentDirection, currentTotal+1));
        }
        
        // Try rotating 90 degrees
        currentDirection = changeDirection(currentDirection);
        if (checkIfCanMove(puzzle, currentDirection)) {
            possibleTotals.add(followMaze(move(puzzle, currentDirection), currentDirection, currentTotal+1001));
        }

        // Try rotating 180 degrees
        currentDirection = changeDirection(currentDirection);
        if (checkIfCanMove(puzzle, currentDirection)) {
            possibleTotals.add(followMaze(move(puzzle, currentDirection), currentDirection, currentTotal+2001));
        }

        // Try rotating 90 degrees the other way
        currentDirection = changeDirection(currentDirection);
        if (checkIfCanMove(puzzle, currentDirection)) {
            possibleTotals.add(followMaze(move(puzzle, currentDirection), currentDirection, currentTotal+1001));
        }

        // Return the smallest route score
        return Collections.min(possibleTotals);

    }

    /**
     * Checks if the reindeer can move in the given direction.
     * Assume the reindeer is not at the edge of the puzzle.
     * @param puzzle the puzzle.
     * @param direction the current direction of the reindeer.
     * @return true if the reindeer can move in the given direction, false if blocked.
     */
    private static boolean checkIfCanMove(ArrayList<String> puzzle, Direction direction) {
        int[] coord = getCoordinates(puzzle);
        return switch (direction) {
            case UP -> puzzle.get(coord[0] - 1).charAt(coord[1]) != '#';
            case RIGHT -> puzzle.get(coord[0]).charAt(coord[1] + 1) != '#';
            case DOWN -> puzzle.get(coord[0] + 1).charAt(coord[1]) != '#';
            default -> puzzle.get(coord[0]).charAt(coord[1] - 1) != '#';
        };
    }

    /**
     * Moves the reindeer in the given direction, replacing current spot with a #.
     * Make a deep copy of the puzzle and assume the reindeer is safe to move.
     * @param existingPuzzle the puzzle.
     * @param direction the direction to move the reindeer.
     * @return the updated puzzle.
     */
    private static ArrayList<String> move(ArrayList<String> existingPuzzle, Direction direction) {

        // Make a deep copy of the puzzle
        ArrayList<String> puzzle = new ArrayList<>();
        for (String row : existingPuzzle) {
            puzzle.add(row);
        }

        // Move them
        int[] coord = getCoordinates(puzzle);
        puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1]) + "#" + puzzle.get(coord[0]).substring(coord[1] + 1));
        switch (direction) {
            case UP -> puzzle.set(coord[0] - 1, puzzle.get(coord[0] - 1).substring(0, coord[1]) + "S" + puzzle.get(coord[0] - 1).substring(coord[1] + 1));
            case DOWN -> puzzle.set(coord[0] + 1, puzzle.get(coord[0] + 1).substring(0, coord[1]) + "S" + puzzle.get(coord[0] + 1).substring(coord[1] + 1));
            case LEFT -> puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1] - 1) + "S" + puzzle.get(coord[0]).substring(coord[1]));
            default -> puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1] + 1) + "S" + puzzle.get(coord[0]).substring(coord[1] + 2));
        }
        return puzzle;
    }

    private static int[] getCoordinates(ArrayList<String> puzzle) {
        for (int i = 0; i < puzzle.size(); i++) {
            if (puzzle.get(i).contains("S")) {
                return new int[]{i, puzzle.get(i).indexOf("S")};
            }
        }
        return new int[]{-1, -1};
    }

    private static Direction changeDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.LEFT;
            case RIGHT -> Direction.UP;
            case DOWN -> Direction.RIGHT;
            case LEFT -> Direction.DOWN;
        };
    }

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }

}
