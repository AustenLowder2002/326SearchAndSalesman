package search_problems;

import core_search.Problem;
import core_search.Tuple;

import java.util.ArrayList;
import java.util.List;

public class SlidingTileProblem implements Problem<int[], Integer> {

    private final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private final int size;

    public SlidingTileProblem(int size) {
        this.size = size;
    }

    @Override
    public int[] initialState() {
        return new int[size];
    }

    @Override
    public int[] goalState() {
        return flatten(GOAL_STATE);
    }

    @Override
    public List<Tuple<int[], Integer>> execution(int[] state) {
        List<Tuple<int[], Integer>> actions = new ArrayList<>();

        // Find the position of the empty tile (0)
        int emptyIndex = findEmptyIndex(state);

        // Check for possible movements and add them to the list of actions
        if (emptyIndex % size > 0) { // Left movement
            int[] newState = swapTiles(state, emptyIndex, emptyIndex - 1);
            actions.add(new Tuple<>(newState, 0, 1));
        }
        if (emptyIndex % size < size - 1) { // Right movement
            int[] newState = swapTiles(state, emptyIndex, emptyIndex + 1);
            actions.add(new Tuple<>(newState, 0, 1));
        }
        if (emptyIndex >= size) { // Up movement
            int[] newState = swapTiles(state, emptyIndex, emptyIndex - size);
            actions.add(new Tuple<>(newState, 0 , 1));
        }
        if (emptyIndex < size * (size - 1)) { // Down movement
            int[] newState = swapTiles(state, emptyIndex, emptyIndex + size);
            actions.add(new Tuple<>(newState, 0, 1)); 
        }

        return actions;
    }

    @Override
    public void printState(int[] state) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(state[i * size + j] + " ");
            }
            System.out.println();
        }
    }

    // Helper method to find the index of the empty tile (0)
    private int findEmptyIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // Helper method to swap two tiles in the state
    private int[] swapTiles(int[] state, int index1, int index2) {
        int[] newState = state.clone();
        int temp = newState[index1];
        newState[index1] = newState[index2];
        newState[index2] = temp;
        return newState;
    }

    // Helper method to flatten a 2D array into a 1D array
    private int[] flatten(int[][] array) {
        int[] result = new int[array.length * array[0].length];
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[k++] = array[i][j];
            }
        }
        return result;
    }
}

