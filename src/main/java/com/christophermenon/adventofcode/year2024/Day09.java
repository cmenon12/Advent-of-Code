package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day09 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 9);

        // Create and fill the disk map
        ArrayList<Integer> diskMap = new ArrayList<>();
        for (int i = 0; i < puzzle.length()-1; i++) {
            int val = Integer.parseInt(puzzle.substring(i, i+1));
            if (i % 2 == 0) {
                if (val == 0) System.err.println("we're trying to add a file that's zero blocks long!");
                addToDiskMap(diskMap, i / 2, val);
            } else {
                addToDiskMap(diskMap, null, val);
            }
        }

        // Apply compacting
        while (!isCompactingComplete(diskMap)) {
            int earliestNullIndex = diskMap.indexOf(null);
            int lastValIndex = getIndexOfLastVal(diskMap);
            diskMap.set(earliestNullIndex, diskMap.get(lastValIndex));
            diskMap.set(lastValIndex, null);
        }

        // Calculate the total
        long total = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            Integer val = diskMap.get(i);
            if (val == null) break;
            total += i * val;
        }

        System.out.printf("PART 1: %d%n", total);

    }

    private static void addToDiskMap(ArrayList<Integer> diskMap, Integer val, int count) {
        for (int i = 0; i < count; i++) {
            diskMap.add(val);
        }
    }

    private static int getIndexOfLastVal(ArrayList<Integer> diskMap) {
        for (int i = diskMap.size() - 1; i >= 0; i--) {
            if (diskMap.get(i) != null) return i;
        }
        return -1;
    }

    private static boolean isCompactingComplete(ArrayList<Integer> diskMap) {
        boolean startedNull = false;
        for (Integer val : diskMap) {
            // if the value is null then we've started the null part
            if (val == null) {
                startedNull = true;

            // if the value is not null but we've started the null part, then not compacted
            } else if (startedNull) {
                return false;
            }
        }
        return true;
    }
}
