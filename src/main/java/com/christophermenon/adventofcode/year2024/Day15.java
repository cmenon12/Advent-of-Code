package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day15 {

    public static void main(String[] args) {
        String[] puzzle = PuzzleInput.getAsArray(2024, 15);

        // Read the instructions and puzzle
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
        StringBuilder instructionsBuilder = new StringBuilder();
        boolean atInstructions = false;
        for (String row : puzzle) {
            if (atInstructions) {
                instructionsBuilder.append(row);
            }
            else if (row.isEmpty()) {
                atInstructions = true;
            } else {
                map.add(new ArrayList<>());
                for (char mapItem : row.toCharArray()) {
                    map.get(map.size() - 1).add(mapItem);
                }
            }
        }
        String instructions = instructionsBuilder.toString();

        // Apply each move
        for (char i : instructions.toCharArray()) {
            switch (i) {
                case '>' -> moveRight(map);
                case '<' -> moveLeft(map);
                case 'v' -> moveDown(map);
                default -> moveUp(map);
            }
        }

        // Calculate the total
        int total = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j) == 'O') {
                    total += (100 * i) + j;
                }
            }
        }
        System.out.printf("PART 1: %d%n", total);
    }

    private static void moveUp(ArrayList<ArrayList<Character>> map) {
        int[] coord = getCoordinates(map);
        for (int i = coord[0] - 1; i >= 0; i--) {
            char currentItem = map.get(i).get(coord[1]);
            if (currentItem == '.') {
                char prev = '.';
                for (int j = coord[0]; j >= i; j--) {
                    prev = map.get(j).set(coord[1], prev);
                }
            }
            if (currentItem == '.' || currentItem == '#') break;
        }
    }

    private static void moveDown(ArrayList<ArrayList<Character>> map) {
        int[] coord = getCoordinates(map);
        for (int i = coord[0] + 1; i < map.size(); i++) {
            char currentItem = map.get(i).get(coord[1]);
            if (currentItem == '.') {
                char prev = '.';
                for (int j = coord[0]; j <= i; j++) {
                    prev = map.get(j).set(coord[1], prev);
                }
            }
            if (currentItem == '.' || currentItem == '#') break;
        }
    }

    private static void moveRight(ArrayList<ArrayList<Character>> map) {
        int[] coord = getCoordinates(map);
        for (int i = coord[1] + 1; i < map.get(coord[0]).size(); i++) {
            char currentItem = map.get(coord[0]).get(i);
            if (currentItem == '.') {
                map.get(coord[0]).add(coord[1], '.');
                map.get(coord[0]).remove(i + 1);
            }
            if (currentItem == '.' || currentItem == '#') break;
        }
    }

    private static void moveLeft(ArrayList<ArrayList<Character>> map) {
        int[] coord = getCoordinates(map);
        for (int i = coord[1] - 1; i >= 0; i--) {
            char currentItem = map.get(coord[0]).get(i);
            if (currentItem == '.') {
                map.get(coord[0]).add(coord[1] + 1, '.');
                map.get(coord[0]).remove(i);
            }
            if (currentItem == '.' || currentItem == '#') break;
        }
    }

    private static int[] getCoordinates(ArrayList<ArrayList<Character>> map) {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).contains('@')) {
                return new int[]{i, map.get(i).indexOf('@')};
            }
        }
        return new int[]{-1, -1};
    }
}
