package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day09 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 9);
        ArrayList<Integer> diskMap = createDiskMap(puzzle);

        // Apply compacting
        while (!isCompactingComplete(diskMap)) {
            int earliestNullIndex = diskMap.indexOf(null);
            int lastValIndex = getIndexOfLastVal(diskMap);
            diskMap.set(earliestNullIndex, diskMap.get(lastValIndex));
            diskMap.set(lastValIndex, null);
        }

        // Calculate the part 1 total
        long total = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            Integer val = diskMap.get(i);
            if (val == null) break;
            total += i * val;
        }

        // Complete part 1
        System.out.printf("PART 1: %d%n", total);

        // Apply compacting for part 2
        diskMap = createDiskMap(puzzle);
        int lastVal = (puzzle.length() / 2) - 1;
        for (int val = lastVal; val >= 0; val--) {
            int valLength = diskMap.lastIndexOf(val) - diskMap.indexOf(val) + 1;
            int nullIndex = getEarliestIndexOfNulls(diskMap, valLength);
            if (nullIndex != -1 && nullIndex < diskMap.lastIndexOf(val)) {
                while (diskMap.indexOf(val) != -1) {
                    diskMap.set(diskMap.indexOf(val), null);
                }
                for (int i = nullIndex; i < nullIndex+valLength; i++) {
                    diskMap.set(i, val);
                }
            }
        }

        // Calculate the part 2 total
        total = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            Integer val = diskMap.get(i);
            if (val == null) continue;
            total += i * val;
        }

        // Complete part 2
        System.out.printf("PART 2: %d%n", total);


    }

    private static ArrayList<Integer> createDiskMap(String puzzle) {
        ArrayList<Integer> diskMap = new ArrayList<>();
        for (int i = 0; i < puzzle.length()-1; i++) {
            int val = Integer.parseInt(puzzle.substring(i, i+1));
            if (i % 2 == 0) {
                if (val == 0) System.err.println("we're trying to add a file that's zero blocks long!");
                for (int j = 0; j < val; j++) {
                    diskMap.add(i / 2);
                }
            } else {
                for (int j = 0; j < val; j++) {
                    diskMap.add(null);
                }
            }
        }
        return diskMap;
    }

    private static int getIndexOfLastVal(ArrayList<Integer> diskMap) {
        for (int i = diskMap.size() - 1; i >= 0; i--) {
            if (diskMap.get(i) != null) return i;
        }
        return -1;
    }

    private static int getEarliestIndexOfNulls(ArrayList<Integer> diskMap, int targetLength) {
        int currentLength = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            if (diskMap.get(i) == null) {
                currentLength += 1;
                if (currentLength == targetLength) {
                    return i - targetLength + 1;
                }
            } else {
                currentLength = 0;
            }
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
