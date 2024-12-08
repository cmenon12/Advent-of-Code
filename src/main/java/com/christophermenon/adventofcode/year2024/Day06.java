package com.christophermenon.adventofcode.year2024;


import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day06 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 6)));
        Direction direction = Direction.UP;

        // Add . at the start and end of each row
        for (int i = 0; i < puzzle.size(); i++) {
            puzzle.set(i, "." + puzzle.get(i) + ".");
        }

        // Add 1 row of ... at the top and bottom of the puzzle
        puzzle.add(0, ".".repeat(puzzle.get(0).length()));
        puzzle.add(".".repeat(puzzle.get(0).length()));

        // Move round the puzzle while not at the edge
        int[] coord = getCoordinates(puzzle);
        while (coord[0] != 0 && coord[0] != puzzle.size() - 1 && coord[1] != 0 && coord[1] != puzzle.get(0).length() - 1) {
            if (checkIfCanMove(puzzle, direction)) {
                puzzle = move(puzzle, direction);
            } else {
                direction = changeDirection(direction);
            }
            coord = getCoordinates(puzzle);
        }

        // Count the number of Xs in the puzzle
        int total = 0;
        for (String row : puzzle) {
            total += row.chars().filter(ch -> ch == 'X').count();
        }
        System.out.printf("PART 1: %d%n", total);

    }

    private static int[] getCoordinates(ArrayList<String> puzzle) {
        for (int i = 0; i < puzzle.size(); i++) {
            if (puzzle.get(i).contains("^")) {
                return new int[]{i, puzzle.get(i).indexOf("^")};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Changes the direction of the guard based on the current direction.
     * @param direction the current direction of the guard.
     * @return the new direction of the guard.
     */
    private static Direction changeDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    /**
     * Checks if the guard can move in the given direction.
     * Assume the guard is not at the edge of the puzzle.
     * @param puzzle the puzzle.
     * @param direction the current direction of the guard.
     * @return true if the guard can move in the given direction, false if blocked.
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
     * Moves the guard in the given direction, replacing current spot with an X.
     * Assume the guard is safe to move and not at the edge of the puzzle.
     * @param puzzle the puzzle.
     * @param direction the direction to move the guard.
     * @return the updated puzzle.
     */
    private static ArrayList<String> move(ArrayList<String> puzzle, Direction direction) {
        int[] coord = getCoordinates(puzzle);
        puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1]) + "X" + puzzle.get(coord[0]).substring(coord[1] + 1));
        switch (direction) {
            case UP -> puzzle.set(coord[0] - 1, puzzle.get(coord[0] - 1).substring(0, coord[1]) + "^" + puzzle.get(coord[0] - 1).substring(coord[1] + 1));
            case DOWN -> puzzle.set(coord[0] + 1, puzzle.get(coord[0] + 1).substring(0, coord[1]) + "^" + puzzle.get(coord[0] + 1).substring(coord[1] + 1));
            case LEFT -> puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1] - 1) + "^" + puzzle.get(coord[0]).substring(coord[1]));
            default -> puzzle.set(coord[0], puzzle.get(coord[0]).substring(0, coord[1] + 1) + "^" + puzzle.get(coord[0]).substring(coord[1] + 2));
        }
        return puzzle;
    }
}

enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;
}
