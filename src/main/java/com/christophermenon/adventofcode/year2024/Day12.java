package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day12 {

    public static void main(String[] args) {
        ArrayList<String> puzzle = new ArrayList<>(Arrays.asList(PuzzleInput.getAsArray(2024, 12)));

        // Create the solution object and solve it
        Solution12 solution = new Solution12(puzzle);
        solution.solveAll();
        
    }
}

class Solution12 {

    ArrayList<String> puzzle;
    HashSet<ArrayList<Integer>> visitedPlots;
    final int[][] directions = new int[][]{
        {0 ,1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };

    // Create the variables we want across the solution methods
    Solution12(ArrayList<String> puzzle) {
        this.puzzle = puzzle;
        this.visitedPlots = new HashSet<>();
    }

    void solveAll() {
        int total = 0;

        // For each coordinate
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < puzzle.get(i).length(); j++) {
                ArrayList<Integer> coord = new ArrayList<>(List.of(i, j));

                // If the plot hasn't been visited then find it
                if (!visitedPlots.contains(coord)) {
                    total += findPlot(coord);
                }
            }
        }
        System.out.printf("PART 1: %d%n", total);
    }

    int findPlot(ArrayList<Integer> start) {

        // Create a queue of places to visit
        LinkedList<ArrayList<Integer>> toVisit = new LinkedList<>();
        toVisit.add(start);

        char plotSymbol = puzzle.get(start.get(0)).charAt(start.get(1));    
        int area = 0;
        int perimeter = 0;
        while (!toVisit.isEmpty()) {

            // Remove from the queue
            ArrayList<Integer> curr = toVisit.pop();

            // Skip if out of bounds or not valid for this plot; save as perimeter
            if (curr.get(0) < 0 || curr.get(0)  >= puzzle.size() || 
                curr.get(1) < 0 || curr.get(1) >= puzzle.get(0).length() ||
                puzzle.get(curr.get(0)).charAt(curr.get(1)) != plotSymbol) {
                perimeter += 1;
                continue;
            }

            // Add to the area if it is part of the current plot and not yet visited
            if (puzzle.get(curr.get(0)).charAt(curr.get(1)) == plotSymbol && !visitedPlots.contains(curr)) {
                area += 1;
                visitedPlots.add(curr);

                // Find all adjacent coordinates
                for (int[] dir : directions) {
                    ArrayList<Integer> coord = new ArrayList<>(Arrays.asList(curr.get(0) + dir[0], curr.get(1) + dir[1]));
                    toVisit.add(coord);
                }
            }
        }

        return area * perimeter;

    }
}
