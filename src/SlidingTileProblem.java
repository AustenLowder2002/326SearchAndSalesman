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
        Set<int[][]> visitedStates = new HashSet<>();

        System.out.println("Processing state:");
        printState(state);

        // Check if the current state is equal to the goal state
        if (isGoalState(state)) {
            System.out.println("Goal state reached!");
            System.exit(0);
            return successors;
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

                successorState[emptyTileRow][emptyTileCol] = state[newRow][newCol];
                successorState[newRow][newCol] = 0;

                System.out.println("Generated successor state:");
                printState(successorState);

                // Calculate the heuristic cost for the successor state
                int heuristicCost = calculateHeuristic(successorState);

                if (!visitedStates.contains(successorState)) {
                    visitedStates.add(successorState);
                    successors.add(new Tuple<>(successorState, null, heuristicCost));
                } else {
                    System.out.println("Duplicate state. Skipping...");
                }
            }
        }

        System.out.println("Number of successors generated: " + successors.size());

        return successors;
    }

    private boolean isValidMove(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private int[][] cloneState(int[][] state) {
        int numRows = state.length;
        int numCols = state[0].length;
        int[][] clone = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            System.arraycopy(state[i], 0, clone[i], 0, numCols);
        }

        return clone;
    }

    public void printState(int[][] state) {
        for (int[] row : state) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public int calculateHeuristic(int[][] currentState) {
        int misplacedTiles = 0;

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j] != goalState[i][j]) {
                    misplacedTiles++;
                }
            }
        }

        return misplacedTiles;
    }

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

