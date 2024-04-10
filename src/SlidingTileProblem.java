import core_search.Problem;
import core_search.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record SlidingTileProblem(int[][] initialState, int[][] goalState) implements Problem<int[][], Integer> {

    // Method to generate successors for the given state
    public List<Tuple<int[][], Integer>> execution(int[][] state) {
        List<Tuple<int[][], Integer>> successors = new ArrayList<>();
        Set<int[][]> visitedStates = new HashSet<>(); // To keep track of visited states

        // Debug print: Print the current state being processed
        System.out.println("Processing state:");
        printState(state);

        // Check if the current state is equal to the goal state
        if (isGoalState(state)) {
            System.out.println("Goal state reached!");
            return successors; // No need to generate successors if the goal state is reached
        }

        // Find the empty tile position
        int emptyTileRow = -1;
        int emptyTileCol = -1;

        outerloop:
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    emptyTileRow = i;
                    emptyTileCol = j;
                    break outerloop;
                }
            }
        }

        // Generate successor states by moving the empty tile in all possible directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] dir : directions) {
            int newRow = emptyTileRow + dir[0];
            int newCol = emptyTileCol + dir[1];

            if (isValidMove(newRow, newCol, state.length, state[0].length)) {
                int[][] successorState = cloneState(state);

                // Swap the empty tile with the adjacent tile
                successorState[emptyTileRow][emptyTileCol] = state[newRow][newCol];
                successorState[newRow][newCol] = 0;

                // Debug print: Print the generated successor state
                System.out.println("Generated successor state:");
                printState(successorState);

                // Check if the successor state has already been visited
                if (!visitedStates.contains(successorState)) {
                    visitedStates.add(successorState);
                    successors.add(new Tuple<>(successorState, null, 1));
                } else {
                    // Debug print: Print if the successor state is a duplicate
                    System.out.println("Duplicate state. Skipping...");
                }
            }
        }

        // Debug print: Print the number of successors generated
        System.out.println("Number of successors generated: " + successors.size());

        return successors;
    }

    // Method to check if the move is valid within the board bounds
    private boolean isValidMove(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    // Method to clone the state to avoid modifying the original state
    private int[][] cloneState(int[][] state) {
        int numRows = state.length;
        int numCols = state[0].length;
        int[][] clone = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            System.arraycopy(state[i], 0, clone[i], 0, numCols);
        }

        return clone;
    }

    // Method to print the state for debugging purposes
    public void printState(int[][] state) {
        for (int[] row : state) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Method to check if the current state is equal to the goal state
    private boolean isGoalState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != goalState[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

