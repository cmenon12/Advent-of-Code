package com.christophermenon.adventofcode.year2024;


import java.util.ArrayList;
import java.util.Arrays;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day06 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 6)));
        Direction direction = Direction.UP;

        int[] coord = getCoordinates(puzzle);
        System.err.println("");

    }

    private static int[] getCoordinates(ArrayList<String> puzzle) {
        for (int i = 0; i < puzzle.size(); i++) {
            if (puzzle.get(i).contains("^")) {
                return new int[]{i, puzzle.get(i).indexOf("^")};
            }
        }
        return new int[]{-1, -1};
    }

    private static Direction changeDirection(Direction direction) {
        if (direction == Direction.UP) return Direction.RIGHT;
        if (direction == Direction.RIGHT) return Direction.DOWN;
        if (direction == Direction.DOWN) return Direction.LEFT;
        return Direction.UP;
    }

    private static boolean checkIfCanMove(ArrayList<String> puzzle, Direction direction) {

    }
}

enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;
}
