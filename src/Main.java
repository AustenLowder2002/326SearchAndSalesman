import core_search.BaseSearch;
import core_search.Problem;

public class Main {

    public static void main(String[] args) {
        // Test case 1 (8-puzzle):
        Problem<int[][], Integer> problem1 = createSlidingTileProblem(
                new int[][]{{7, 2, 4}, {5, 6, 0}, {8, 3, 1}},
                new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}
        );

        testAlgorithm("Greedy Best-First Sum of Distances", new GBFSOD<>(problem1, new SimplePQ<>()));
        testAlgorithm("A* Sum of Distances", new ASSOD<>(problem1, new SimplePQ<>()));
        testAlgorithm("Greedy Best-First Misplaced Tiles", new GBFMT<>(problem1, new SimplePQ<>()));
        testAlgorithm("A* Misplaced Tiles", new ASMT<>(problem1, new SimplePQ<>()));
        testAlgorithm("BFS", new BFS<>(problem1, new SimplePQ<>()));
        testAlgorithm("DFS", new DFS<>(problem1, new SimplePQ<>()));

        // Test case 2 (15-puzzle):
        Problem<int[][], Integer> problem2 = createSlidingTileProblem(
                new int[][]{{12, 1, 2, 15}, {11, 6, 5, 8}, {7, 10, 9, 4}, {13, 14, 3, 0}},
                new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}}
        );

        testAlgorithm("Greedy Best-First Sum of Distances", new GBFSOD<>(problem2, new SimplePQ<>()));
        testAlgorithm("A* Sum of Distances", new ASSOD<>(problem2, new SimplePQ<>()));
        //
        testAlgorithm("BFS", new BFS<>(problem2, new SimplePQ<>()));
        testAlgorithm("DFS", new DFS<>(problem2, new SimplePQ<>()));
        testAlgorithm("Greedy Best-First Misplaced Tiles", new GBFMT<>(problem2, new SimplePQ<>()));
        testAlgorithm("A* Misplaced Tiles", new ASMT<>(problem2, new SimplePQ<>()));
    }

    private static <S, A> void testAlgorithm(String algorithmName, BaseSearch<S, A> algorithm) {
        System.out.println("Testing " + algorithmName + ":");
        boolean result = algorithm.search();
        System.out.println("Result: " + (result ? "Solved" : "No solution found"));
        System.out.println("-----------");
    }

    private static Problem<int[][], Integer> createSlidingTileProblem(int[][] initialState, int[][] goalState) {
        return new SlidingTileProblem(initialState, goalState);
    }
}


